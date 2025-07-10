package com.springmusicapp.service;

import com.springmusicapp.mapper.BandMapper;
import com.springmusicapp.model.Band;
import com.springmusicapp.repository.BandRepository;

import java.util.List;
import java.util.stream.Collectors;


public abstract class AbstractBandService<T extends Band, D> {

    protected final BandRepository<T> bandRepository;
    private final BandMapper<T, D> bandMapper;

    public AbstractBandService(BandRepository repository, BandMapper<T, D> bandMapper) {
        this.bandRepository = repository;
        this.bandMapper = bandMapper;
    }

    public T findById(Long id) {
        return bandRepository.findById(id).orElseThrow(() -> new IllegalStateException("Band not found with id:" + id));
    }

    public List<T> findByName(String name) {
        List<T> bands = bandRepository.findByName(name);

        if (bands.isEmpty()) {
            throw new IllegalStateException("Band not found with name:" + name);
        }

        return bands;
    }

    public List<T> findByTotalSells(int sells) {
        return bandRepository.findAll().stream().filter(u -> u.getTotalSells() < sells).toList();
    }
}
