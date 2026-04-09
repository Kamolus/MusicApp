package com.springmusicapp.domain.label.dto.event_manager;

import com.springmusicapp.core.base.RegisterTemplate;

public record RegisterEventManagerDTO(
        String id,
        String email,
        String name,
        CreateEventManagerDTO userInput
) implements RegisterTemplate<CreateEventManagerDTO> {
}
