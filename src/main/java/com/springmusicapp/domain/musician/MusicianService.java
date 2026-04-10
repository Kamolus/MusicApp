package com.springmusicapp.domain.musician;

import com.springmusicapp.domain.user.service.AbstractUserService;
import com.springmusicapp.domain.band.BandDTO;
import com.springmusicapp.domain.band.CreateBandDTO;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.band.BandMapper;
import com.springmusicapp.domain.band.Band;
import com.springmusicapp.domain.band.BandRepository;
import com.springmusicapp.security.keycloak.KeycloakRoleService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MusicianService extends AbstractUserService<Musician> {

    private final MusicianRepository musicianRepository;
    private final BandRepository bandRepository;
    private final KeycloakRoleService keycloakRoleService;

    public MusicianService(MusicianRepository musicianRepository,
                           BandRepository bandRepository,
                           KeycloakRoleService keycloakRoleService) {
        super(musicianRepository);
        this.musicianRepository = musicianRepository;
        this.bandRepository = bandRepository;
        this.keycloakRoleService = keycloakRoleService;
    }

    @Transactional
    public MusicianDTO create(RegisterMusicianDTO dto) {

        Musician musician = MusicianMapper.toEntity(dto.userInput());

        Musician savedMusician = super.create(
                musician,
                dto.id(),
                dto.email(),
                dto.name()
        );

        keycloakRoleService.assignRealmRole(musician.getId(), "MUSICIAN");

        return MusicianMapper.toDto(savedMusician);
    }

    public MusicianDTO getById(String id) {
        Musician musician = musicianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Musician", "id", id));
        return MusicianMapper.toDto(musician);
    }

    public List<MusicianDTO> getAll() {
        return musicianRepository.findAll()
                .stream()
                .map(MusicianMapper::toDto)
                .collect(Collectors.toList());
    }

    public void removeById(String id) {
        if (!musicianRepository.existsById(id)) {
            throw new ResourceNotFoundException("Musician", "id", id);
        }
        musicianRepository.deleteById(id);
    }

    public void removeByEmail(String email) {
        if(!musicianRepository.existsByEmail(email)){
            throw new ResourceNotFoundException("Musician", "email", email);
        }
        musicianRepository.deleteByEmail(email);
    }

    @Transactional
    public BandDTO createBandByMusician(CreateBandDTO createBandDto) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserId = jwt.getSubject();

        System.out.println(currentUserId);
        Musician musician = musicianRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Musician", "id", currentUserId));

        if (musician.getCurrentBand() != null) {
            throw new IllegalStateException("Muzyk ma już zespół!");
        }

        Band band = new Band(createBandDto.name());

        band.addMusician(musician);

        bandRepository.save(band);

        return BandMapper.toDto(band);
    }

    public List<MusicianDTO> getAllByCurrentBandId(UUID bandId) {
        return musicianRepository.findAllByCurrentBandId(bandId)
                .stream()
                .map(MusicianMapper::toDto)
                .toList();
    }

    public List<MusicianDTO> findAllByCurrentBandIsNotNull() {
        return musicianRepository.findAllByCurrentBandIsNotNull()
                .stream()
                .map(MusicianMapper::toDto)
                .toList();
    }

    public List<MusicianDTO> searchByName(String name, int page, int size) {

        if (name == null || name.trim().length() < 3) {
            return Collections.emptyList();
        }

        Pageable pageable = PageRequest.of(page, size);

        return musicianRepository.findByNameContainingIgnoreCase(name.trim(), pageable)
                .stream()
                .map(MusicianMapper::toDto)
                .toList();
    }
}

