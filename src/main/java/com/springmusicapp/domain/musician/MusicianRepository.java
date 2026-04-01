package com.springmusicapp.domain.musician;

import com.springmusicapp.domain.label.model.MusicianScout;
import com.springmusicapp.domain.user.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MusicianRepository extends UserRepository<Musician> {
    Optional<Musician> findByStageName(String stageName);
    List<Musician> findAllByCurrentBandIsNotNull();
    List<Musician> findAllByCurrentBandId(UUID bandId);
    List<Musician> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
