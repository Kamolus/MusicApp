package com.springmusicapp.service;

import com.springmusicapp.repository.MusicLabelRepository;
import org.springframework.stereotype.Service;

@Service
public class MusicLabelService {

    private final MusicLabelRepository musicLabelRepository;

    public MusicLabelService(MusicLabelRepository musicLabelRepository) {
        this.musicLabelRepository = musicLabelRepository;
    }
}
