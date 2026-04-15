package com.springmusicapp.domain.musician;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateMusicianDTO(
        @NotBlank(message = "Stage name cannot be empty")
        String stageName,

        @NotEmpty(message = "Types cannot be empty")
        Set<String> types
) {
}
