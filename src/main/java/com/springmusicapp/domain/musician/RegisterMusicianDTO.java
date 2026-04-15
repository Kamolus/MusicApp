package com.springmusicapp.domain.musician;

import com.springmusicapp.core.base.RegisterTemplate;

public record RegisterMusicianDTO(
        String id,
        String email,
        String name,
        CreateMusicianDTO userInput
) implements RegisterTemplate<CreateMusicianDTO> {
}
