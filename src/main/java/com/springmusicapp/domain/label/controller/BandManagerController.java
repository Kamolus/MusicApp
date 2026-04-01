package com.springmusicapp.domain.label.controller;

import com.springmusicapp.domain.label.dto.BandManagerDTO;
import com.springmusicapp.domain.label.dto.CreateBandManagerDTO;
import com.springmusicapp.domain.label.service.BandManagerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/band_managers")
public class BandManagerController {

    private final BandManagerService bandManagerService;

    public BandManagerController(BandManagerService bandManagerService) {
        this.bandManagerService = bandManagerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BandManagerDTO> getBandManager(@PathVariable UUID id) {
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
    public ResponseEntity<String> deleteBandManager(@PathVariable UUID id) {
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
    public ResponseEntity<BandManagerDTO> createBandManager(@Valid @RequestBody CreateBandManagerDTO dto) {
        BandManagerDTO bandManagerDTO = bandManagerService.create(dto);
        return ResponseEntity.ok(bandManagerDTO);
    }
}
