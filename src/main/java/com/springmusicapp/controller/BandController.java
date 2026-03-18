package com.springmusicapp.controller;

import com.springmusicapp.dto.AlbumDTO;
import com.springmusicapp.dto.BandDTO;
import com.springmusicapp.dto.CreateAlbumDTO;
import com.springmusicapp.dto.CreateBandDTO;
import com.springmusicapp.mapper.BandMapper;
import com.springmusicapp.model.Band;
import com.springmusicapp.service.AlbumService;
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
    private final AlbumService albumService;

    public BandController(BandService bandService, AlbumService albumService) {
        this.bandService = bandService;
        this.albumService = albumService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BandDTO> getBandById(@PathVariable Long id) {
        BandDTO band = bandService.findById(id);
        return ResponseEntity.ok(band);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BandDTO>> getBandsByName(@RequestParam String name) {
        List<BandDTO> bands = bandService.findByName(name);
        return ResponseEntity.ok(bands);
    }

    @GetMapping
    public ResponseEntity<List<BandDTO>> getAllBands() {
        List<BandDTO> bands = bandService.findAll();
        return ResponseEntity.ok(bands);
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

        return ResponseEntity.ok("Musician assigned successfully to the band");
    }

    @PostMapping("/{bandId}/albums")
    public ResponseEntity<AlbumDTO> addAlbumToBand(
            @PathVariable Long bandId,
            @Valid @RequestBody CreateAlbumDTO createDto) {

        AlbumDTO newAlbum = albumService.createAlbumForBand(bandId, createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAlbum);
    }
}
