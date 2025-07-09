package com.springmusicapp.service;

import com.springmusicapp.DTO.MusicianDTO;
import com.springmusicapp.model.Musician;
import com.springmusicapp.repository.MusicianRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.EnumSet;
import java.util.List;

@Service
public class MusicianService extends AbstractUserService {
    private final MusicianRepository musicianRepository;

    public MusicianService(MusicianRepository musicianRepository) {
        super(musicianRepository);
        this.musicianRepository = musicianRepository;
    }

    public Musician create(MusicianDTO dto) {
        Musician musician = new Musician(
                dto.getName(),
                dto.getEmail(),
                dto.getStageName(),
                EnumSet.copyOf(dto.getTypes())
        );
        return musicianRepository.save(musician);
    }

    public Musician getById(Long id) {
        return musicianRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Musician not found with id: " + id));
    }

    public List<Musician> getAll() {
        return musicianRepository.findAll();
    }
}

