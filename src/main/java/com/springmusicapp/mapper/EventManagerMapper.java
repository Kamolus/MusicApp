package com.springmusicapp.mapper;

import com.springmusicapp.dto.CreateEventManagerDTO;
import com.springmusicapp.dto.EventDTO;
import com.springmusicapp.dto.EventManagerDTO;
import com.springmusicapp.model.EventManager;

import java.util.List;

public class EventManagerMapper {

    public static EventManagerDTO toDto(EventManager eventManager) {
        if (eventManager == null) return null;

        List<EventDTO> events = eventManager.getEvents() != null ?
                eventManager.getEvents().stream()
                        .map(event -> new EventDTO(
                                        event.getName(),
                                        event.getDate()
                                )).toList() : List.of();

        return new EventManagerDTO(
                eventManager.getName(),
                eventManager.getEmail(),
                eventManager.getAreaOfOperation(),
                events
        );
    }

    public static EventManager toEntity(CreateEventManagerDTO dto) {
        if (dto == null) return null;

        EventManager eventManager = new EventManager();
        eventManager.setName(dto.name());
        eventManager.setEmail(dto.email());
        eventManager.setPassword(dto.password());

        if (dto.areaOfOperation() != null) {
            eventManager.setAreaOfOperation(dto.areaOfOperation());
        }

        if(dto.hireDate() != null) {
            eventManager.setHireDate(dto.hireDate());
        }

        return eventManager;
    }
}
