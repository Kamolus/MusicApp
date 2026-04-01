package com.springmusicapp.domain.label.service;

import com.springmusicapp.domain.label.dto.CreateEventManagerDTO;
import com.springmusicapp.domain.label.dto.EventManagerDTO;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.label.mapper.EventManagerMapper;
import com.springmusicapp.domain.label.model.EventManager;
import com.springmusicapp.domain.label.repository.EventManagerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventManagerService extends AbstractEmployeeService<EventManager> {

    private final EventManagerRepository eventManagerRepository;
    private final PasswordEncoder passwordEncoder;

    public EventManagerService(EventManagerRepository eventManagerRepository, PasswordEncoder passwordEncoder) {
        super(eventManagerRepository);
        this.eventManagerRepository = eventManagerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public EventManagerDTO create(CreateEventManagerDTO dto) {
        EventManager eventManager = EventManagerMapper.toEntity(dto);

        String encodedPassword = passwordEncoder.encode(eventManager.getPassword());
        eventManager.setPassword(encodedPassword);

        EventManager saved = super.create(eventManager);
        return EventManagerMapper.toDto(saved);
    }

    public List<EventManagerDTO> getAll() {
        return eventManagerRepository.findAll()
                .stream()
                .map(EventManagerMapper::toDto)
                .collect(Collectors.toList());
    }

    public EventManagerDTO getById(UUID id) {
        EventManager eventManager = eventManagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EventManager", "id", id));
        return EventManagerMapper.toDto(eventManager);
    }

    public void deleteById(UUID id) {
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
