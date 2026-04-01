package com.springmusicapp.domain.band;

import com.springmusicapp.core.exception.BusinessLogicException;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.musician.Musician;
import com.springmusicapp.domain.musician.MusicianRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BandService{

    protected final BandRepository bandRepository;
    private final MusicianRepository musicianRepository;

    public BandService(BandRepository repository, MusicianRepository musicianRepository) {
        this.bandRepository = repository;
        this.musicianRepository = musicianRepository;
    }

    public BandDTO findById(UUID id) {
        Band band = bandRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Band", "id", id));
        return BandMapper.toDto(band);
    }

    public List<BandDTO> findByName(String name) {
        List<Band> bands = bandRepository.findByName(name);

        if (bands.isEmpty()) {
            throw new ResourceNotFoundException("Band", "name", name);
        }

        return bands.stream().map(BandMapper::toDto).toList();
    }

    public void assignMusician(UUID bandId, UUID musicianId){
        Musician musician = musicianRepository.findById(musicianId)
                .orElseThrow(() -> new ResourceNotFoundException("Musician", "id", musicianId));

        if (!musician.isAvailable()) {
            throw new BusinessLogicException("Musician is already in a band","ERR_MUSICIAN_ALREADY_ASSIGNED");
        }

        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new ResourceNotFoundException("Band", "id", bandId));

        band.addMusician(musician);
        musicianRepository.save(musician);
    }

    public BandDTO createBand(CreateBandDTO dto) {
        Band band = BandMapper.toEntity(dto);

        Band savedBand = bandRepository.save(band);

        return BandMapper.toDto(savedBand);
    }

    public List<BandDTO> findAll() {
        return bandRepository.findAll().stream().map(BandMapper::toDto).toList();
    }

}
