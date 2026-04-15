package com.springmusicapp.domain.label.service;

import com.springmusicapp.domain.label.dto.musician_scout.MusicianScoutDTO;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.label.dto.musician_scout.RegisterMusicianScoutDTO;
import com.springmusicapp.domain.label.mapper.MusicianScoutMapper;
import com.springmusicapp.domain.label.model.MusicianScout;
import com.springmusicapp.domain.band.repository.BandRepository;
import com.springmusicapp.domain.label.repository.MusicianScoutRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class MusicianScoutService extends AbstractEmployeeService<MusicianScout> {

    private final MusicianScoutRepository musicianScoutRepository;
    private final BandRepository bandRepository;

    public MusicianScoutService(MusicianScoutRepository musicianScoutRepository, BandRepository bandRepository) {
        super(musicianScoutRepository);
        this.musicianScoutRepository = musicianScoutRepository;
        this.bandRepository = bandRepository;
    }

    public MusicianScoutDTO create(@Valid @RequestBody RegisterMusicianScoutDTO dto) {
        MusicianScout musicianScout = MusicianScoutMapper.toEntity(dto.userInput());


        MusicianScout saved  = super.create(
                musicianScout,
                dto.id(),
                dto.email(),
                dto.name()
                );

        return MusicianScoutMapper.toDto(saved);
    }

    public List<MusicianScoutDTO> getAll() {
        return musicianScoutRepository.findAll()
                .stream()
                .map(MusicianScoutMapper::toDto)
                .toList();
    }

    public MusicianScoutDTO findById(String id) {
        MusicianScout musicianScout = musicianScoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MusicianScout", "id", id));
        return MusicianScoutMapper.toDto(musicianScout);
    }

    public void deleteById(String id) {
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
