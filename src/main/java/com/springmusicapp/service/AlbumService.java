package com.springmusicapp.service;

import com.springmusicapp.repository.AlbumRepository;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {

    protected final AlbumRepository albumRepository;


    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }
}
