package com.springmusicapp.service;

import com.springmusicapp.exception.BandException;
import com.springmusicapp.exception.MusicianException;
import com.springmusicapp.model.Musician;
import com.springmusicapp.model.Band;
import com.springmusicapp.repository.BandRepository;
import com.springmusicapp.repository.MusicianRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BandService{

    protected final BandRepository bandRepository;
    private final MusicianRepository musicianRepository;

    public BandService(BandRepository repository, MusicianRepository musicianRepository) {
        this.bandRepository = repository;
        this.musicianRepository = musicianRepository;
    }

    public Band findById(Long id) {
        return bandRepository.findById(id).orElseThrow(() -> new BandException("Band not found with id:" + id,
                HttpStatus.NOT_FOUND));
    }

    public List<Band> findByName(String name) {
        List<Band> bands = bandRepository.findByName(name);

        if (bands.isEmpty()) {
            throw new BandException("Band not found with name:" + name, HttpStatus.NOT_FOUND);
        }

        return bands;
    }

    public void assignMusician(Long bandId, Long musicianId){
        Musician musician = musicianRepository.findById(musicianId)
                .orElseThrow(() -> new MusicianException("Musician not found with id:" + musicianId,
                        HttpStatus.NOT_FOUND));

        if (!musician.isAvailable()) {
            throw new MusicianException("Musician is already in a band", HttpStatus.CONFLICT);
        }

        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new BandException("Band not found with id:" + bandId, HttpStatus.NOT_FOUND));

        band.addMusician(musician);
        musicianRepository.save(musician);
    }

    public Band createBand(Band band) {
        return bandRepository.save(band);
    }

    public List<Band> findAll() {
        List<Band> bands = bandRepository.findAll();

        if (bands.isEmpty()) {
            throw new BandException("Band not found", HttpStatus.NOT_FOUND);
        }

        return bands;
    }

}
