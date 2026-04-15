package com.springmusicapp.domain.label.dto.music_label;

import jakarta.validation.constraints.NotBlank;

public record CreateMusicLabelDTO(
        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "Tax number cannot be empty")
        String taxNumber
) {
}
