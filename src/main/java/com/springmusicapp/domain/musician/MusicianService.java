package com.springmusicapp.domain.musician;

import com.springmusicapp.core.base.UserRoleAssignmentEvent;
import com.springmusicapp.domain.band.dto.BandDTO;
import com.springmusicapp.domain.band.dto.CreateBandDTO;
import com.springmusicapp.domain.band.mapper.BandMapper;
import com.springmusicapp.domain.band.model.Band;
import com.springmusicapp.domain.band.model.BandRole;
import com.springmusicapp.domain.band.repository.BandRepository;
import com.springmusicapp.domain.user.service.AbstractUserService;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.security.keycloak.KeycloakRoleListener;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MusicianService extends AbstractUserService<Musician> {

    private final MusicianRepository musicianRepository;
    private final BandRepository bandRepository;
    private final KeycloakRoleListener keycloakEventPublisher;

    public MusicianService(MusicianRepository musicianRepository,
                           BandRepository bandRepository,
                           KeycloakRoleListener keycloakEventPublisher) {
        super(musicianRepository);
        this.musicianRepository = musicianRepository;
        this.bandRepository = bandRepository;
        this.keycloakEventPublisher = keycloakEventPublisher;
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

        keycloakEventPublisher.onUserRoleAssignment(new UserRoleAssignmentEvent(savedMusician.getId(), "MUSICIAN"));

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

        Musician musician = musicianRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Musician", "id", currentUserId));

        Band band = new Band(createBandDto.name());

        band.addMusician(musician, BandRole.FOUNDER);

        bandRepository.save(band);

        return BandMapper.toDto(band);
    }

    public List<MusicianDTO> getAllByCurrentBandId(UUID bandId) {
        return musicianRepository.findAllByBandId(bandId)
                .stream()
                .map(MusicianMapper::toDto)
                .toList();
    }

    public List<MusicianDTO> findAllByCurrentBandIsNotNull() {
        return musicianRepository.findAllByMembershipsIsNotEmpty()
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

