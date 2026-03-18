package com.springmusicapp.service;

import com.springmusicapp.dto.CreateMusicianDTO;
import com.springmusicapp.dto.MusicianDTO;
import com.springmusicapp.exception.BusinessLogicException;
import com.springmusicapp.exception.ResourceNotFoundException;
import com.springmusicapp.mapper.MusicianMapper;
import com.springmusicapp.model.Band;
import com.springmusicapp.model.Musician;
import com.springmusicapp.repository.BandRepository;
import com.springmusicapp.repository.MusicianRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicianService extends AbstractUserService<Musician> {

    private final MusicianRepository musicianRepository;
    private final BandRepository bandRepository;

    public MusicianService(MusicianRepository musicianRepository,
                           BandRepository bandRepository) {
        super(musicianRepository);
        this.musicianRepository = musicianRepository;
        this.bandRepository = bandRepository;
    }

    public Musician create(CreateMusicianDTO dto) {
        Musician musician = MusicianMapper.toEntity(dto);
        return musicianRepository.save(musician);
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

    public void assignToBand(Long musicianId, Long bandId){
        Musician musician = musicianRepository.findById(musicianId)
            .orElseThrow(() -> new ResourceNotFoundException("Musician", "id", musicianId));

        if (!musician.isAvailable()) {
            throw new BusinessLogicException("Musician is already in a band", "ERR_Musician_already_in_band");
        }

        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new ResourceNotFoundException("Band", "id", bandId));

        musician.assignToBand(band);
        musicianRepository.save(musician);
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
}

