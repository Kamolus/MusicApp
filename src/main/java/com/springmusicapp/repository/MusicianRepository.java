package com.springmusicapp.repository;

import com.springmusicapp.model.Musician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicianRepository extends JpaRepository<Musician, Integer> {
}
