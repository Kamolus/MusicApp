package com.springmusicapp.service;

import com.springmusicapp.DTO.MusicianDTO;
import com.springmusicapp.mapper.MusicianMapper;
import com.springmusicapp.model.Band;
import com.springmusicapp.model.Musician;
import com.springmusicapp.repository.MusicianRepository;
import com.springmusicapp.repository.PopularBandRepository;
import com.springmusicapp.repository.UnpopularBandRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MusicianService extends AbstractUserService<Musician> {

    private final MusicianRepository musicianRepository;
    private final PopularBandRepository popularBandRepository;
    private final UnpopularBandRepository unpopularBandRepository;

    public MusicianService(MusicianRepository musicianRepository,
                           PopularBandRepository popularBandRepository,
                           UnpopularBandRepository unpopularBandRepository) {
        super(musicianRepository);
        this.musicianRepository = musicianRepository;
        this.popularBandRepository = popularBandRepository;
        this.unpopularBandRepository = unpopularBandRepository;
    }

    public Musician create(MusicianDTO dto) {
        Musician musician = MusicianMapper.toEntity(dto);
        return musicianRepository.save(musician);
    }

    public MusicianDTO getById(Long id) {
        Musician musician = musicianRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Musician not found with id: " + id));
        return MusicianMapper.toDto(musician);
    }

    public List<MusicianDTO> getAll() {
        return musicianRepository.findAll()
                .stream()
                .map(MusicianMapper::toDto)
                .collect(Collectors.toList());
    }

    public void assignToBand(Long musicianId, Long bandId) {
        Musician musician = musicianRepository.findById(musicianId)
            .orElseThrow(() -> new IllegalStateException("Musician not found"));

        if (!musician.isAvailable()) {
            throw new IllegalStateException("Musician is already in a band");
        }

        Band band = popularBandRepository.findById(bandId)
                .map(b -> (Band) b)
                .orElseGet(() -> unpopularBandRepository.findById(bandId)
                        .map(b -> (Band) b)
                        .orElseThrow(() -> new IllegalStateException("No band found with id: " + bandId)));

        musician.assignToBand(band);
        musicianRepository.save(musician);
    }
}

