package com.springmusicapp.controllers;

import com.springmusicapp.DTO.PopularBandDTO;
import com.springmusicapp.service.PopularBandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/bands/popular")
public class PopularBandController {

    private final PopularBandService popularBandService;

    public PopularBandController(PopularBandService service) {
        this.popularBandService = service;
    }


    @GetMapping("/earned_money")
    public ResponseEntity<List<PopularBandDTO>> getBandsByEarnedMoneyGreaterThan(@RequestParam double minMoney) {
        List<PopularBandDTO> popularBandDTO = popularBandService.findByEarnedMoneyGreaterThan(minMoney);

        if (popularBandDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(popularBandDTO);
    }

    @GetMapping("/rich_with_minimal_albums")
    public ResponseEntity<List<PopularBandDTO>> findRichBandsWithManyAlbums
            (@RequestParam double minMoney, int minAlbums) {

        List<PopularBandDTO> popularBandDTO = popularBandService.findRichBandsWithManyAlbums(minMoney,minAlbums);

        if (popularBandDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(popularBandDTO);
    }
}

