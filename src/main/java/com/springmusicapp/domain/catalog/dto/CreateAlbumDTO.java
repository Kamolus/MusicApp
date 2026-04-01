package com.springmusicapp.domain.catalog.dto;

public record CreateAlbumDTO(
        String title,
        String releaseDate,
        String imageUrl
) {
}
