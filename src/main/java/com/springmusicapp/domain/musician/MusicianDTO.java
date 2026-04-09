package com.springmusicapp.domain.musician;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

public record MusicianDTO(
        String id,
        String name,
        String email,
        String stageName,
        String currentBand,
        List<String> types
) {
}
