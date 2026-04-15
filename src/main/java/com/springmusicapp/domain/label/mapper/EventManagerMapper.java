package com.springmusicapp.domain.label.mapper;

import com.springmusicapp.domain.label.dto.event_manager.CreateEventManagerDTO;
import com.springmusicapp.domain.event.EventDTO;
import com.springmusicapp.domain.label.dto.event_manager.EventManagerDTO;
import com.springmusicapp.domain.label.model.EventManager;

import java.util.List;

public class EventManagerMapper {

    public static EventManagerDTO toDto(EventManager eventManager) {
        if (eventManager == null) return null;

        List<EventDTO> events = eventManager.getEvents() != null ?
                eventManager.getEvents().stream()
                        .map(event -> new EventDTO(
                                        event.getId(),
                                        event.getName(),
                                        event.getDate()
                                )).toList() : List.of();

        return new EventManagerDTO(
                eventManager.getId(),
                eventManager.getName(),
                eventManager.getEmail(),
                eventManager.getAreaOfOperation(),
                events
        );
    }

    public static EventManager toEntity(CreateEventManagerDTO dto) {
        if (dto == null) return null;

        EventManager eventManager = new EventManager();

        if (dto.areaOfOperation() != null) {
            eventManager.setAreaOfOperation(dto.areaOfOperation());
        }

        if(dto.hireDate() != null) {
            eventManager.setHireDate(dto.hireDate());
        }

        return eventManager;
    }
}
