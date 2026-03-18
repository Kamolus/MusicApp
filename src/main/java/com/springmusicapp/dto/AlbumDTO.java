package com.springmusicapp.dto;

import java.util.List;

public record AlbumDTO(
        Long id,
        String title,
        String releaseDate,
        String bandName,
        String imageUrl,
        List<SongForAlbumDTO> songs
) {
}
