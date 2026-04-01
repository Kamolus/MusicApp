package com.springmusicapp.domain.catalog.dto;

import java.util.UUID;

public record SongDTO(
        UUID id,
        String title,
        int durationMs,
        int views,
        String spotifyId,
        String albumTitle) {
}
