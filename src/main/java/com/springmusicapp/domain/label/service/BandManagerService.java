package com.springmusicapp.domain.label.service;

import com.springmusicapp.domain.label.dto.BandManagerDTO;
import com.springmusicapp.domain.label.dto.CreateBandManagerDTO;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.label.mapper.BandManagerMapper;
import com.springmusicapp.domain.label.model.BandManager;
import com.springmusicapp.domain.label.repository.BandManagerRepository;
import com.springmusicapp.domain.band.BandRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BandManagerService extends AbstractEmployeeService<BandManager> {

    private final BandRepository bandRepository;
    private final BandManagerRepository bandManagerRepository;
    private final PasswordEncoder passwordEncoder;

    public BandManagerService(BandRepository bandRepository, BandManagerRepository bandManagerRepository, PasswordEncoder passwordEncoder) {
        super(bandManagerRepository);
        this.bandManagerRepository = bandManagerRepository;
        this.bandRepository = bandRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public BandManagerDTO create(CreateBandManagerDTO dto) {
        BandManager bandManager = BandManagerMapper.toEntity(dto);

        String encodedPassword = passwordEncoder.encode(bandManager.getPassword());
        bandManager.setPassword(encodedPassword);

        BandManager savedBandManager = super.create(bandManager);
        return BandManagerMapper.toDTO(savedBandManager);
    }

    public BandManagerDTO findById(UUID id) {
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

    public void deleteById(UUID id) {
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
