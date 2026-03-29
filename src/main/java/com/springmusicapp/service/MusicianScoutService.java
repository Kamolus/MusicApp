package com.springmusicapp.service;

import com.springmusicapp.repository.BandRepository;
import com.springmusicapp.repository.MusicianScoutRepository;
import org.springframework.stereotype.Service;

@Service
public class MusicianScoutService extends AbstractEmployeeService{

    private final MusicianScoutRepository musicianScoutRepository;
    private final BandRepository bandRepository;

    public MusicianScoutService(MusicianScoutRepository musicianScoutRepository, BandRepository bandRepository) {
        super(musicianScoutRepository);
        this.musicianScoutRepository = musicianScoutRepository;
        this.bandRepository = bandRepository;
    }
}
