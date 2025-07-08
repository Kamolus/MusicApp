package com.springmusicapp.repository;

import com.springmusicapp.model.Musician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Long> {
    Optional<Musician> findByStageName(String stageName);
}
