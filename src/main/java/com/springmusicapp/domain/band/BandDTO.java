package com.springmusicapp.domain.band;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record BandDTO(
        UUID bandId,
        @NotBlank
        String name,
        List<String> musiciansName,
        List<String> albumsTitles
) {
}
