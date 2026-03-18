package com.springmusicapp.controller;

import com.springmusicapp.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping("/{songId}/studio-musicians/{musicianId}")
    public ResponseEntity<Void> addStudioMusicianToSong(
            @PathVariable Long songId,
            @PathVariable Long musicianId) {

        songService.addStudioMusicianToSong(songId, musicianId);
        return ResponseEntity.ok().build();
    }
}
