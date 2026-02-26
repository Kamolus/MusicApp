package com.springmusicapp.repository;

import com.springmusicapp.model.Musician;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicianRepository extends UserRepository<Musician> {
    Optional<Musician> findByStageName(String stageName);


}
