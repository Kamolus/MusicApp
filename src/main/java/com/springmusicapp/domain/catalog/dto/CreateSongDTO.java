package com.springmusicapp.domain.catalog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSongDTO(

        @NotBlank(message = "Song title cannot be empty")
        String title,

        @NotNull(message = "Duration must be provided")
        @Min(value = 1, message = "Duration must be greater than 0")
        Integer durationMs
) {
}