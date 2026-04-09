package com.springmusicapp.domain.musician;

import com.springmusicapp.domain.band.BandDTO;
import com.springmusicapp.domain.band.CreateBandDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/musicians")
public class MusicianController {

    private final MusicianService musicianService;

    public MusicianController(MusicianService musicianService) {
        this.musicianService = musicianService;
    }

    @PostMapping
    public ResponseEntity<MusicianDTO> create(
            @Valid @RequestBody CreateMusicianDTO dto,
            @AuthenticationPrincipal Jwt jwt) {

        RegisterMusicianDTO command = new RegisterMusicianDTO(
                jwt.getSubject(),
                jwt.getClaimAsString("email"),
                jwt.getClaimAsString("name"),
                dto
        );

        MusicianDTO saved = musicianService.create(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicianDTO> getById(@PathVariable String id) {
        MusicianDTO musician = musicianService.getById(id);
        return ResponseEntity.ok(musician);
    }

    @GetMapping
    public ResponseEntity<List<MusicianDTO>> getAll() {
        return ResponseEntity.ok(musicianService.getAll());
    }

    @PreAuthorize("principal.id == #id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        musicianService.removeById(id);
        return ResponseEntity.ok("Musician removed");
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<String> deleteByEmail(@PathVariable String email) {
        musicianService.removeByEmail(email);
        return ResponseEntity.ok("Musician removed");
    }

    @PostMapping("/band")
    public ResponseEntity<BandDTO> createBand(@Valid @RequestBody CreateBandDTO bandDto) {
        BandDTO newBand = musicianService.createBandByMusician(bandDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBand);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MusicianDTO>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        int safeSize = Math.min(size, 50);

        List<MusicianDTO> musicians = musicianService.searchByName(name, page, safeSize);
        return ResponseEntity.ok(musicians);
    }

    @GetMapping("/band/{bandId}")
    public ResponseEntity<List<MusicianDTO>> searchByBand(@PathVariable UUID bandId) {
        List<MusicianDTO> musicians = musicianService.getAllByCurrentBandId(bandId);
        return ResponseEntity.ok(musicians);
    }

    @GetMapping("/in_band")
    public ResponseEntity<List<MusicianDTO>> searchByInBand(){
        List<MusicianDTO> musicians = musicianService.findAllByCurrentBandIsNotNull();
        return ResponseEntity.ok(musicians);
    }
}
