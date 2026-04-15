package com.springmusicapp.domain.catalog.controller;

import com.springmusicapp.domain.catalog.dto.CreateSongDTO;
import com.springmusicapp.domain.catalog.dto.SongDTO;
import com.springmusicapp.domain.catalog.service.SongService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping("/{songId}/studio-musicians/{musicianId}")
    public ResponseEntity<Void> addStudioMusicianToSong(
            @PathVariable UUID songId,
            @PathVariable String musicianId) {

        songService.addStudioMusicianToSong(songId, musicianId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<SongDTO>> getAllSongs() {
        return ResponseEntity.ok(songService.findAllSongs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> getSongById(@PathVariable UUID id) {
        return ResponseEntity.ok(songService.findSongById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongDTO> updateSong(
            @PathVariable UUID id,
            @Valid @RequestBody CreateSongDTO updateDto) {
        return ResponseEntity.ok(songService.updateSong(id, updateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable UUID id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }
}
