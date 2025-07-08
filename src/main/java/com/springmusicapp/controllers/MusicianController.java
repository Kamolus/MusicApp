package com.springmusicapp.controllers;

import com.springmusicapp.DTO.MusicianDTO;
import com.springmusicapp.model.Musician;
import com.springmusicapp.model.MusicianType;
import com.springmusicapp.repository.MusicianRepository;
import com.springmusicapp.service.MusicianService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.EnumSet;
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
    public ResponseEntity<Musician> getById(@PathVariable Long id) {
        Musician musician = musicianService.getById(id);
        return ResponseEntity.ok(musician);
    }

    @GetMapping
    public ResponseEntity<List<Musician>> getAll() {
        return ResponseEntity.ok(musicianService.getAll());
    }

    @GetMapping("/get_example")
    public List<Musician> getMusicians() {
        return List.of(
                new Musician(
                        "James",
                        "k@gmail.com",
                        "Jane",
                        EnumSet.of(MusicianType.DRUMMER, MusicianType.GUITARIST)
                ),
                new Musician(
                        "Kamil",
                        "l@gmail.com",
                        "Kamolus",
                        EnumSet.of(MusicianType.DRUMMER, MusicianType.GUITARIST)
                )
        );
    }
}
