package com.springmusicapp.domain.catalog.dto;

import java.util.UUID;

public record SongForAlbumDTO(
        UUID songId,
        String title,
        int views,
        int durationMs
) {
}
