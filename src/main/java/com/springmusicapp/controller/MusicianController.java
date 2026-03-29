package com.springmusicapp.controller;

import com.springmusicapp.dto.BandDTO;
import com.springmusicapp.dto.CreateBandDTO;
import com.springmusicapp.dto.CreateMusicianDTO;
import com.springmusicapp.dto.MusicianDTO;
import com.springmusicapp.model.Musician;
import com.springmusicapp.service.MusicianService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<MusicianDTO> create(@Valid @RequestBody CreateMusicianDTO dto) {
        MusicianDTO saved = musicianService.create(dto);
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


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        musicianService.removeById(id);
        return ResponseEntity.ok("Musician removed");
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<String> deleteByEmail(@PathVariable String email) {
        musicianService.removeByEmail(email);
        return ResponseEntity.ok("Musician removed");
    }

    @PostMapping("/{musicianId}/bands")
    public ResponseEntity<BandDTO> createBand(
            @PathVariable Long musicianId,
            @Valid @RequestBody CreateBandDTO bandDto) {

        BandDTO newBand = musicianService.createBandByMusician(musicianId, bandDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBand);
    }

}
