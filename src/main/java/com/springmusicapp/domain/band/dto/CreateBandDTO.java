package com.springmusicapp.domain.band.dto;

import jakarta.validation.constraints.NotBlank;
public record CreateBandDTO(
        @NotBlank(message = "Band's name cannot be empty")
        String name
) {
}
