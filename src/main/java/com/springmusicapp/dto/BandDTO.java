package com.springmusicapp.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record BandDTO(
        @NotBlank
        String name,
        List<String> musiciansName,
        List<String> albumsTitles
) {
}
