package com.springmusicapp.domain.label.dto.event_manager;

import com.springmusicapp.domain.event.EventDTO;

import java.util.List;

public record EventManagerDTO(
        String id,
        String name,
        String email,
        String areaOfOperation,
        List<EventDTO> events
) {
}
