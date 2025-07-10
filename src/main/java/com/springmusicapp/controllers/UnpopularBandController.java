package com.springmusicapp.controllers;

import com.springmusicapp.DTO.UnpopularBandDTO;
import com.springmusicapp.service.UnpopularBandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/bands/unpopular")
public class UnpopularBandController {

    private final UnpopularBandService unpopularBandService;

    public UnpopularBandController(UnpopularBandService service) {
        this.unpopularBandService = service;
    }

    @GetMapping("/target_group")
    public ResponseEntity<List<UnpopularBandDTO>> findByTargetGroup(String targetGroup) {
        List<UnpopularBandDTO> unpopularBandDTO = unpopularBandService.findByTargetGroup(targetGroup);

        if (unpopularBandDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(unpopularBandDTO);
    }
}
