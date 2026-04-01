package com.springmusicapp.domain.band;

import jakarta.validation.constraints.NotBlank;
public record CreateBandDTO(
        @NotBlank(message = "Band's name cannot be empty")
        String name
) {
}
