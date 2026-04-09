package com.springmusicapp.domain.label.service;

import com.springmusicapp.domain.label.dto.event_manager.EventManagerDTO;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.label.dto.event_manager.RegisterEventManagerDTO;
import com.springmusicapp.domain.label.mapper.EventManagerMapper;
import com.springmusicapp.domain.label.model.EventManager;
import com.springmusicapp.domain.label.repository.EventManagerRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventManagerService extends AbstractEmployeeService<EventManager> {

    private final EventManagerRepository eventManagerRepository;

    public EventManagerService(EventManagerRepository eventManagerRepository) {
        super(eventManagerRepository);
        this.eventManagerRepository = eventManagerRepository;
    }

    public EventManagerDTO create(@Valid @RequestBody RegisterEventManagerDTO dto) {
        EventManager eventManager = EventManagerMapper.toEntity(dto.userInput());

        EventManager saved = super.create(
                eventManager,
                dto.id(),
                dto.email(),
                dto.name()
        );

        return EventManagerMapper.toDto(saved);
    }

    public List<EventManagerDTO> getAll() {
        return eventManagerRepository.findAll()
                .stream()
                .map(EventManagerMapper::toDto)
                .collect(Collectors.toList());
    }

    public EventManagerDTO getById(String id) {
        EventManager eventManager = eventManagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EventManager", "id", id));
        return EventManagerMapper.toDto(eventManager);
    }

    public void deleteById(String id) {
        if (!eventManagerRepository.existsById(id)) {
            throw new ResourceNotFoundException("EventManager", "id", id);
        }
        eventManagerRepository.deleteById(id);
    }

    public void deleteByEmail(String email) {
        if(!eventManagerRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("EventManager", "email", email);
        }
        eventManagerRepository.deleteByEmail(email);
    }
}
