package com.springmusicapp.domain.musician;

import com.springmusicapp.domain.user.model.User;
import com.springmusicapp.domain.user.service.AbstractUserService;
import com.springmusicapp.domain.band.BandDTO;
import com.springmusicapp.domain.band.CreateBandDTO;
import com.springmusicapp.core.exception.BusinessLogicException;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.band.BandMapper;
import com.springmusicapp.domain.band.Band;
import com.springmusicapp.domain.band.BandRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MusicianService extends AbstractUserService<Musician> {

    private final MusicianRepository musicianRepository;
    private final BandRepository bandRepository;
    private final PasswordEncoder passwordEncoder;

    public MusicianService(MusicianRepository musicianRepository,
                           BandRepository bandRepository, PasswordEncoder passwordEncoder) {
        super(musicianRepository);
        this.musicianRepository = musicianRepository;
        this.bandRepository = bandRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MusicianDTO create(CreateMusicianDTO dto) {
        Musician musician = MusicianMapper.toEntity(dto);

        String encodedPassword = passwordEncoder.encode(musician.getPassword());
        musician.setPassword(encodedPassword);

        Musician savedMusician = super.create(musician);
        return MusicianMapper.toDto(savedMusician);
    }

    public MusicianDTO getById(UUID id) {
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

    public void removeById(UUID id) {
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
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID currentUserId = currentUser.getId();

        Musician musician = musicianRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Musician", "id", currentUserId));

        if (musician.getCurrentBand() != null) {
            throw new BusinessLogicException(
                    "This musician is already in a band",
                    "ERR_MUSICIAN_ALREADY_IN_BAND"
            );
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

