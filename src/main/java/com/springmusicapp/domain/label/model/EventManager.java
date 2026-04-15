package com.springmusicapp.domain.label.model;

import com.springmusicapp.domain.event.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "event_managers")
public class EventManager extends Employee {


    @NotBlank
    @Column(nullable = false)
    private String areaOfOperation;

    @OneToMany(mappedBy = "eventManager", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Event> events = new ArrayList<>();

    public EventManager(String id, String name, String email, LocalDate hireDate, double salary, String areaOfOperation) {
        super(id, name, email, hireDate, salary);
        setAreaOfOperation(areaOfOperation);
    }

    public EventManager(){}


    public void setAreaOfOperation(String areaOfOperation) {
        if(areaOfOperation == null || areaOfOperation.isBlank()) {
            throw new IllegalArgumentException("Area of operation cannot be null or empty");
        }
        this.areaOfOperation = areaOfOperation;
    }

    public void addEvent(Event event) {
        if(!events.contains(event) && event != null) {
            events.add(event);
            event.setEventManager(this);
        }
    }

    public void removeEvent(Event event) {
        if(events.remove(event)) {
            event.setEventManager(null);
        }
    }

    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }


    @Override
    public String toString() {
        return "EventManager{" +
                "events=" + events +
                '}';
    }
}
