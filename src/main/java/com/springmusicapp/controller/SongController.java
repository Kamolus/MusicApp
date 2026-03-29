package com.springmusicapp.controller;

import com.springmusicapp.dto.CreateSongDTO;
import com.springmusicapp.dto.SongDTO;
import com.springmusicapp.service.SongService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<SongDTO>> getAllSongs() {
        return ResponseEntity.ok(songService.findAllSongs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(songService.findSongById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongDTO> updateSong(
            @PathVariable Long id,
            @Valid @RequestBody CreateSongDTO updateDto) {
        return ResponseEntity.ok(songService.updateSong(id, updateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }
}
