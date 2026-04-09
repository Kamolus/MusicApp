package com.springmusicapp.domain.label.service;

import com.springmusicapp.domain.label.dto.band_manager.BandManagerDTO;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.label.dto.band_manager.RegisterBandManagerDTO;
import com.springmusicapp.domain.label.mapper.BandManagerMapper;
import com.springmusicapp.domain.label.model.BandManager;
import com.springmusicapp.domain.label.repository.BandManagerRepository;
import com.springmusicapp.domain.band.BandRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BandManagerService extends AbstractEmployeeService<BandManager> {

    private final BandRepository bandRepository;
    private final BandManagerRepository bandManagerRepository;

    public BandManagerService(BandRepository bandRepository, BandManagerRepository bandManagerRepository) {
        super(bandManagerRepository);
        this.bandManagerRepository = bandManagerRepository;
        this.bandRepository = bandRepository;
    }

    public BandManagerDTO create(@Valid @RequestBody RegisterBandManagerDTO dto) {
        BandManager bandManager = BandManagerMapper.toEntity(dto.userInput());

        BandManager savedBandManager = super.create(
                bandManager,
                dto.id(),
                dto.email(),
                dto.name()
                );

        return BandManagerMapper.toDTO(savedBandManager);
    }

    public BandManagerDTO findById(String id) {
        BandManager manager = bandManagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BandManager", "id", id));
        return BandManagerMapper.toDTO(manager);
    }

    public List<BandManagerDTO> getAll() {
        return bandManagerRepository.findAll()
                .stream()
                .map(BandManagerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(String id) {
        if (!bandManagerRepository.existsById(id)) {
            throw new ResourceNotFoundException("BandManager", "id", id);
        }
        bandManagerRepository.deleteById(id);
    }

    public void deleteByEmail(String email) {
        if(!bandManagerRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("BandManager", "email", email);
        }
        bandManagerRepository.deleteByEmail(email);
    }
}
