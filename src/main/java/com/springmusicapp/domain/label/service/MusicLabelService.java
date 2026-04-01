package com.springmusicapp.domain.label.service;

import com.springmusicapp.domain.label.repository.MusicLabelRepository;
import org.springframework.stereotype.Service;

@Service
public class MusicLabelService {

    private final MusicLabelRepository musicLabelRepository;

    public MusicLabelService(MusicLabelRepository musicLabelRepository) {
        this.musicLabelRepository = musicLabelRepository;
    }
}
