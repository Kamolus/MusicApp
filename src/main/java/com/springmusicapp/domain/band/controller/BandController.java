package com.springmusicapp.domain.band.controller;

import com.springmusicapp.domain.band.dto.BandDTO;
import com.springmusicapp.domain.band.dto.CreateBandDTO;
import com.springmusicapp.domain.band.invite.InviteMusicianRequest;
import com.springmusicapp.domain.band.service.BandService;
import com.springmusicapp.domain.catalog.dto.AlbumDTO;
import com.springmusicapp.domain.catalog.dto.CreateAlbumDTO;
import com.springmusicapp.domain.catalog.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bands")
@Tag(name = "Bands", description = "Endpoints for managing music bands and their memberships")
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

    @Operation(
            summary = "Invite a musician to the band",
            description = "Sends an invitation to a specified musician. The user making the request must have the FOUNDER or ADMIN role within the band."
    )
    @ApiResponse(responseCode = "201", description = "Invitation successfully created and event published")
    @ApiResponse(responseCode = "400", description = "Invalid request payload or business validation failed (e.g., already a member)")
    @ApiResponse(responseCode = "403", description = "Access denied. Insufficient permissions to invite members")
    @ApiResponse(responseCode = "404", description = "Band or target musician not found")
    @PostMapping("/{bandId}/invites")
    public ResponseEntity<Void> inviteMusicianToBand(
            @PathVariable UUID bandId,
            @Valid @RequestBody InviteMusicianRequest request) {

        bandService.inviteMusician(bandId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
