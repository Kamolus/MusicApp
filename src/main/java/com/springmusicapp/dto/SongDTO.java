package com.springmusicapp.dto;

public record SongDTO(
        Long id,
        String title,
        int durationMs,
        int views,
        String spotifyId,
        String albumTitle) {
}
