package com.springmusicapp.domain.musician;

import java.util.List;

public record MusicianDTO(
        String id,
        String name,
        String email,
        String stageName,
        List<String> bands,
        List<String> types
) {
}
