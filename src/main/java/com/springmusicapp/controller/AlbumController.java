package com.springmusicapp.controller;

import com.springmusicapp.dto.AlbumDTO;
import com.springmusicapp.dto.CreateSongDTO;
import com.springmusicapp.dto.SongForAlbumDTO;
import com.springmusicapp.service.AlbumService;
import com.springmusicapp.service.SongService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/albums")
public class AlbumController {
    private final AlbumService albumService;
    private final SongService songService;

    public AlbumController(AlbumService albumService, SongService songService) {
        this.albumService = albumService;
        this.songService = songService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> getAlbumById(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.findById(id));
    }

    @PostMapping("/{albumId}/songs")
    public ResponseEntity<SongForAlbumDTO> addSongToAlbum(
            @PathVariable Long albumId,
            @Valid @RequestBody CreateSongDTO createDto) {
        SongForAlbumDTO newSong = songService.addSongToAlbum(albumId, createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSong);
    }

    @GetMapping("/{albumId}/songs")
    public ResponseEntity<List<SongForAlbumDTO>> getSongsForAlbum(@PathVariable Long albumId) {
        return ResponseEntity.ok(songService.getSongsSummaryForAlbum(albumId));
    }
}
