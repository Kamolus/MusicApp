package com.springmusicapp.domain.label.dto;

import java.util.UUID;

public record MusicianScoutDTO(
        UUID id,
        String name,
        String email,
        String phoneNumber
) {
}
