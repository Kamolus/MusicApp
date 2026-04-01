package com.springmusicapp.domain.catalog.dto;

import java.util.List;
import java.util.UUID;

public record AlbumDTO(
        UUID id,
        String title,
        String releaseDate,
        String bandName,
        String imageUrl,
        List<SongForAlbumDTO> songs
) {
}
