package com.springmusicapp.service;

import com.springmusicapp.dto.CreateMusicianDTO;
import com.springmusicapp.dto.MusicianDTO;
import com.springmusicapp.exception.BandException;
import com.springmusicapp.exception.MusicianException;
import com.springmusicapp.mapper.MusicianMapper;
import com.springmusicapp.model.Band;
import com.springmusicapp.model.Musician;
import com.springmusicapp.repository.BandRepository;
import com.springmusicapp.repository.MusicianRepository;
import org.springframework.http.HttpStatus;
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
                .orElseThrow(() -> new MusicianException("Musician not found with id: " + id, HttpStatus.NOT_FOUND));
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
            .orElseThrow(() -> new MusicianException("Musician not found with id: " + musicianId, HttpStatus.NOT_FOUND));

        if (!musician.isAvailable()) {
            throw new MusicianException("Musician is already in a band", HttpStatus.CONFLICT);
        }

        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new BandException("No band found with id: " + bandId, HttpStatus.NOT_FOUND));

        musician.assignToBand(band);
        musicianRepository.save(musician);
    }

    public void removeById(Long id) {
        if (!musicianRepository.existsById(id)) {
            throw new MusicianException("Musician not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        musicianRepository.deleteById(id);
    }

    public void removeByEmail(String email) {
        if(!musicianRepository.existsByEmail(email)){
            throw new MusicianException("Musician not found with email: " + email, HttpStatus.NOT_FOUND);
        }
        musicianRepository.deleteByEmail(email);
    }
}

