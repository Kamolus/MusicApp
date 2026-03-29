package com.springmusicapp.service;

import com.springmusicapp.dto.BandDTO;
import com.springmusicapp.dto.CreateBandDTO;
import com.springmusicapp.dto.CreateMusicianDTO;
import com.springmusicapp.dto.MusicianDTO;
import com.springmusicapp.exception.BusinessLogicException;
import com.springmusicapp.exception.ResourceNotFoundException;
import com.springmusicapp.mapper.BandMapper;
import com.springmusicapp.mapper.MusicianMapper;
import com.springmusicapp.model.Band;
import com.springmusicapp.model.Musician;
import com.springmusicapp.repository.BandRepository;
import com.springmusicapp.repository.MusicianRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public MusicianDTO getById(Long id) {
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

    public void removeById(Long id) {
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
    public BandDTO createBandByMusician(Long musicianId, CreateBandDTO createBandDto) {
        Musician musician = musicianRepository.findById(musicianId)
                .orElseThrow(() -> new ResourceNotFoundException("Musician", "id", musicianId));

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
}

