package com.springmusicapp.domain.label.controller;

import com.springmusicapp.domain.label.dto.band_manager.BandManagerDTO;
import com.springmusicapp.domain.label.dto.band_manager.CreateBandManagerDTO;
import com.springmusicapp.domain.label.dto.band_manager.RegisterBandManagerDTO;
import com.springmusicapp.domain.label.service.BandManagerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/band_managers")
public class BandManagerController {

    private final BandManagerService bandManagerService;

    public BandManagerController(BandManagerService bandManagerService) {
        this.bandManagerService = bandManagerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BandManagerDTO> getBandManager(@PathVariable String id) {
        BandManagerDTO bandManagerDTO = bandManagerService.findById(id);
        return ResponseEntity.ok(bandManagerDTO);
    }

    @GetMapping
    public ResponseEntity<List<BandManagerDTO>> getAllBandManagers() {
        List<BandManagerDTO> bandManagerDTOS = bandManagerService.getAll();
        return ResponseEntity.ok(bandManagerDTOS);
    }

    @PreAuthorize("principal.id == #id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBandManager(@PathVariable String id) {
        bandManagerService.deleteById(id);
        return ResponseEntity.ok("Band manager removed");
    }

    @PreAuthorize("principal.email == #email")
    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteBandManagerByEmail(@PathVariable String email) {
        bandManagerService.deleteByEmail(email);
        return ResponseEntity.ok("Band manager removed");
    }

    @PostMapping
    public ResponseEntity<BandManagerDTO> createBandManager(@Valid @RequestBody CreateBandManagerDTO dto,
                                                            @AuthenticationPrincipal Jwt jwt) {
        RegisterBandManagerDTO command = new RegisterBandManagerDTO(
                jwt.getSubject(),
                jwt.getClaimAsString("email"),
                jwt.getClaimAsString("name"),
                dto
        );

        BandManagerDTO saved = bandManagerService.create(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
