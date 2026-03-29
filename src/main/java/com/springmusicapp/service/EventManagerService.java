package com.springmusicapp.service;

import com.springmusicapp.repository.EventManagerRepository;
import org.springframework.stereotype.Service;

@Service
public class EventManagerService extends AbstractEmployeeService{

    private final EventManagerRepository eventManagerRepository;

    public EventManagerService(EventManagerRepository eventManagerRepository) {
        super(eventManagerRepository);
        this.eventManagerRepository = eventManagerRepository;
    }
}
