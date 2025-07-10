package com.springmusicapp.controllers;

import com.springmusicapp.DTO.MusicianDTO;
import com.springmusicapp.model.Musician;
import com.springmusicapp.service.MusicianService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/musicians")
public class MusicianController {

    private final MusicianService musicianService;

    public MusicianController(MusicianService musicianService) {
        this.musicianService = musicianService;
    }

    @PostMapping
    public ResponseEntity<Musician> create(@RequestBody MusicianDTO dto) {
        Musician saved = musicianService.create(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicianDTO> getById(@PathVariable Long id) {
        MusicianDTO musician = musicianService.getById(id);
        return ResponseEntity.ok(musician);
    }

    @GetMapping
    public ResponseEntity<List<MusicianDTO>> getAll() {
        return ResponseEntity.ok(musicianService.getAll());
    }

    @PutMapping("/{musicianId}/assign-band/{bandId}")
    public ResponseEntity<String> assignToBand(@PathVariable Long musicianId, @PathVariable Long bandId) {
        musicianService.assignToBand(musicianId, bandId);
        return ResponseEntity.ok("Musician assigned to band");
    }
}
