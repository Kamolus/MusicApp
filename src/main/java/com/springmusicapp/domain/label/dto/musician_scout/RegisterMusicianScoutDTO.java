package com.springmusicapp.domain.label.dto.musician_scout;

import com.springmusicapp.core.base.RegisterTemplate;

public record RegisterMusicianScoutDTO(
        String id,
        String email,
        String name,
        CreateMusicianScoutDTO userInput
) implements RegisterTemplate<CreateMusicianScoutDTO> {
}
