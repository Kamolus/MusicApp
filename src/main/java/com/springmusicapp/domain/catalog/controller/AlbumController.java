package com.springmusicapp.domain.catalog.controller;

import com.springmusicapp.domain.catalog.dto.AlbumDTO;
import com.springmusicapp.domain.catalog.dto.CreateSongDTO;
import com.springmusicapp.domain.catalog.dto.SongForAlbumDTO;
import com.springmusicapp.domain.catalog.service.AlbumService;
import com.springmusicapp.domain.catalog.service.SongService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<AlbumDTO> getAlbumById(@PathVariable UUID id) {
        return ResponseEntity.ok(albumService.findById(id));
    }

    @PostMapping("/{albumId}/songs")
    public ResponseEntity<SongForAlbumDTO> addSongToAlbum(
            @PathVariable UUID albumId,
            @Valid @RequestBody CreateSongDTO createDto) {
        SongForAlbumDTO newSong = songService.addSongToAlbum(albumId, createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSong);
    }

    @GetMapping("/{albumId}/songs")
    public ResponseEntity<List<SongForAlbumDTO>> getSongsForAlbum(@PathVariable UUID albumId) {
        return ResponseEntity.ok(songService.getSongsSummaryForAlbum(albumId));
    }
}
