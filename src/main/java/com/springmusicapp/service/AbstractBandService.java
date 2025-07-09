package com.springmusicapp.service;

import com.springmusicapp.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractBandService<T extends Band> {
    protected final JpaRepository<T, Long> repository;

    public AbstractBandService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public T findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalStateException("Band not found with id:" + id));
    }

    public List<T> findByName(String name) {
        return repository.findAll()
                .stream()
                .filter(u -> u.getName().equalsIgnoreCase(name))
                .toList();
    }

    public List<T> findByTotalSells(int sells) {
        return repository.findAll().stream().filter(u -> u.getTotalSells() < sells).toList();
    }
}
