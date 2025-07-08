package com.springmusicapp.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Event{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany
    private List<Performance> performances = new ArrayList<>();

    @OneToOne
    private EventManager eventManager;

    public Event(String name, LocalDate date) {
        setName(name);
        setDate(date);
    }

    public Event() {}

    public void setName(String name) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Event name cannot be empty");
        }
        this.name = name;
    }

    public void setDate(LocalDate date) {
        if (date == null || date.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Event date must be in the future");
        }
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getEventManager() {
        return eventManager.toString();
    }

    public void setEventManager(EventManager eventManager) {
        if (eventManager != null && !eventManager.equals(this.eventManager)){
            this.eventManager = eventManager;
            eventManager.addEvent(this);
        }
    }

    public void removeEventManager() {
        if (eventManager != null){
            eventManager.removeEvent(this);
            eventManager = null;
        }
    }

    public void addBandToPerformance(Band band, boolean isMainBand) {
        if(performances.stream().noneMatch(performance -> performance.getBand().equals(band))){
            new Performance(this, band, isMainBand);
        }
    }

    public void removePerformance(Performance performance) {
        if(performances.contains(performance)){
            performances.remove(performance);
            performance.removePerformance();
        }
    }

    public void addPerformance(Performance performance) {
        if(performances.contains(performance)){
            throw new IllegalArgumentException("Performance already exists");
        }
        performances.add(performance);
    }

    public List<Band> getBands() {
        return performances.stream()
                .map(Performance::getBand)
                .collect(Collectors.toList());
    }

    public List<Band> getMainBands() {
        return performances.stream()
                .filter(Performance::isMainBand)
                .map(Performance::getBand)
                .collect(Collectors.toList());
    }

    public List<Band> getSupportingBands() {
        return performances.stream()
                .filter(p -> !p.isMainBand())
                .map(Performance::getBand)
                .collect(Collectors.toList());
    }

    public List<Performance> getPerformances() {
        return Collections.unmodifiableList(performances);
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", totalPerformances=" + performances.size() +
                '}';
    }
}
