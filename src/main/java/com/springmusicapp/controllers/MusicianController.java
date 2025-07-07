package com.springmusicapp.controllers;

import com.springmusicapp.DTO.MusicianDTO;
import com.springmusicapp.model.Musician;
import com.springmusicapp.model.MusicianType;
import com.springmusicapp.repository.MusicianRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.List;

@RestController
@RequestMapping("/api/musicians")
public class MusicianController {

    private final MusicianRepository musicianRepository;

    public MusicianController(MusicianRepository musicianRepository) {
        this.musicianRepository = musicianRepository;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody MusicianDTO dto) {
        Musician musician = new Musician(
                dto.getName(),
                dto.getEmail(),
                dto.getStageName(),
                EnumSet.copyOf(dto.getTypes())
        );

        Musician saved = musicianRepository.save(musician);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Musician> getAll() {
        return musicianRepository.findAll();
    }

    @GetMapping
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
