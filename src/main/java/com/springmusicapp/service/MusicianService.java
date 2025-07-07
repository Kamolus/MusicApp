package com.springmusicapp.service;

import com.springmusicapp.model.Musician;
import com.springmusicapp.repository.MusicianRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicianService {
    private MusicianRepository musicianRepository;

    public MusicianService(MusicianRepository musicianRepository) {
        this.musicianRepository = musicianRepository;
    }

    public List<Musician> getMusicians() {
        return musicianRepository.findAll();
    }
}

