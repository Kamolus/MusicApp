package com.springmusicapp.repository;

import com.springmusicapp.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BandRepository<T extends Band> extends JpaRepository<T, Long> {

    List<T> findByName(String name);
    Optional<T> findById(Long id);
}
