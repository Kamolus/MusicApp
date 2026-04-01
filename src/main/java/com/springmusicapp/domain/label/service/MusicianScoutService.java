package com.springmusicapp.domain.label.service;

import com.springmusicapp.domain.label.dto.CreateMusicianScoutDTO;
import com.springmusicapp.domain.label.dto.MusicianScoutDTO;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.label.mapper.MusicianScoutMapper;
import com.springmusicapp.domain.label.model.MusicianScout;
import com.springmusicapp.domain.band.BandRepository;
import com.springmusicapp.domain.label.repository.MusicianScoutRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MusicianScoutService extends AbstractEmployeeService<MusicianScout> {

    private final MusicianScoutRepository musicianScoutRepository;
    private final BandRepository bandRepository;
    private final PasswordEncoder passwordEncoder;

    public MusicianScoutService(MusicianScoutRepository musicianScoutRepository, BandRepository bandRepository, PasswordEncoder passwordEncoder) {
        super(musicianScoutRepository);
        this.musicianScoutRepository = musicianScoutRepository;
        this.bandRepository = bandRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MusicianScoutDTO create(CreateMusicianScoutDTO dto) {
        MusicianScout musicianScout = MusicianScoutMapper.toEntity(dto);

        String encodedPassword = passwordEncoder.encode(musicianScout.getPassword());
        musicianScout.setPassword(encodedPassword);

        MusicianScout saved  = super.create(musicianScout);
        return MusicianScoutMapper.toDto(saved);
    }

    public List<MusicianScoutDTO> getAll() {
        return musicianScoutRepository.findAll()
                .stream()
                .map(MusicianScoutMapper::toDto)
                .toList();
    }

    public MusicianScoutDTO findById(UUID id) {
        MusicianScout musicianScout = musicianScoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MusicianScout", "id", id));
        return MusicianScoutMapper.toDto(musicianScout);
    }

    public void deleteById(UUID id) {
        if (!musicianScoutRepository.existsById(id)) {
            throw new ResourceNotFoundException("MusicianScout", "id", id);
        }
        musicianScoutRepository.deleteById(id);
    }

    public void deletedByEmail(String email) {
        if (!musicianScoutRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("MusicianScout", "email", email);
        }
        musicianScoutRepository.deleteByEmail(email);
    }
}
