package com.springmusicapp.dto;

import jakarta.validation.constraints.NotBlank;

public record MusicianScoutDTO(
        @NotBlank
        String name,

        @NotBlank
        String email,

        String phoneNumber
) {
}
