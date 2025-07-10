package com.springmusicapp.repository;

import com.springmusicapp.model.Musician;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicianRepository extends UserRepository<Musician> {
    Optional<Musician> findByStageName(String stageName);
}
