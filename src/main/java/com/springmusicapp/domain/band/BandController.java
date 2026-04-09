package com.springmusicapp.domain.band;

import com.springmusicapp.domain.catalog.dto.AlbumDTO;
import com.springmusicapp.domain.catalog.dto.CreateAlbumDTO;
import com.springmusicapp.domain.catalog.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<BandDTO> getBandById(@PathVariable UUID id) {
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
        BandDTO saved = bandService.createBand(createDto);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{bandId}/musicians/{musicianId}")
    public ResponseEntity<String> assignMusicianToBand(@PathVariable UUID bandId, @PathVariable String musicianId) {
        bandService.assignMusician(bandId, musicianId);

        return ResponseEntity.ok("Musician assigned successfully to the band");
    }

    @PostMapping("/{bandId}/albums")
    public ResponseEntity<AlbumDTO> addAlbumToBand(
            @PathVariable UUID bandId,
            @Valid @RequestBody CreateAlbumDTO createDto) {

        AlbumDTO newAlbum = albumService.createAlbumForBand(bandId, createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAlbum);
    }
}
