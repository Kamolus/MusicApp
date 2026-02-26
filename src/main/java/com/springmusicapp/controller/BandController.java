package com.springmusicapp.controller;

import com.springmusicapp.dto.BandDTO;
import com.springmusicapp.dto.CreateBandDTO;
import com.springmusicapp.mapper.BandMapper;
import com.springmusicapp.model.Band;
import com.springmusicapp.service.BandService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bands")
public class BandController {

    private final BandService bandService;

    public BandController(BandService bandService) {
        this.bandService = bandService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BandDTO> getBandById(@PathVariable Long id) {
        Band band = bandService.findById(id);
        BandDTO responseDto = BandMapper.toDto(band);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BandDTO>> getBandsByName(@RequestParam String name) {
        List<Band> bands = bandService.findByName(name);

        List<BandDTO> responseDtos = bands.stream()
                .map(BandMapper::toDto)
                .toList();

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping
    public ResponseEntity<List<BandDTO>> getAllBands() {
        // Tu zakładam, że masz metodę findAll() w BandService
        List<Band> bands = bandService.findAll();
        List<BandDTO> responseDtos = bands.stream()
                .map(BandMapper::toDto)
                .toList();
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping
    public ResponseEntity<BandDTO> createBand(@Valid @RequestBody CreateBandDTO createDto) {
        Band newBand = BandMapper.toEntity(createDto);

        Band savedBand = bandService.createBand(newBand);

        return ResponseEntity.status(HttpStatus.CREATED).body(BandMapper.toDto(savedBand));
    }

    @PostMapping("/{bandId}/musicians/{musicianId}")
    public ResponseEntity<String> assignMusicianToBand(@PathVariable Long bandId, @PathVariable Long musicianId) {
        bandService.assignMusician(bandId, musicianId);

        return ResponseEntity.ok("Musician assigned successfully to the band!");
    }
}
