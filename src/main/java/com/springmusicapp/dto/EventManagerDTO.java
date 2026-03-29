package com.springmusicapp.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record EventManagerDTO(
        @NotBlank
        String name,

        @NotBlank
        String email,

        String areaOfOperation,

        List<EventDTO> events
) {
}
