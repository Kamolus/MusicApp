package com.springmusicapp.controller;

import com.springmusicapp.dto.CreateMusicianDTO;
import com.springmusicapp.dto.MusicianDTO;
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
    public ResponseEntity<Musician> create(@RequestBody CreateMusicianDTO dto) {
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        musicianService.removeById(id);
        return ResponseEntity.ok("Musician removed");
    }

    @DeleteMapping("delete/by_email/{email}")
    public ResponseEntity<String> deleteByEmail(@PathVariable String email) {
        musicianService.removeByEmail(email);
        return ResponseEntity.ok("Musician removed");
    }

}
