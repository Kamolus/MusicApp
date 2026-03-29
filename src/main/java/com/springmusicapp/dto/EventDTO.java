package com.springmusicapp.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record EventDTO(
        @NotBlank
        String name,

        @NotBlank
        LocalDate date
) {
}
