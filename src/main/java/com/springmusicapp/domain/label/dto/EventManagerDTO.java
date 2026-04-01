package com.springmusicapp.domain.label.dto;

import com.springmusicapp.domain.event.EventDTO;

import java.util.List;
import java.util.UUID;

public record EventManagerDTO(
        UUID id,
        String name,
        String email,
        String areaOfOperation,
        List<EventDTO> events
) {
}
