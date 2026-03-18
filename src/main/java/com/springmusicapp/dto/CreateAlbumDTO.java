package com.springmusicapp.dto;

import lombok.Getter;
import lombok.Setter;

public record CreateAlbumDTO(
        String title,
        String releaseDate,
        String imageUrl
) {
}
