package com.springmusicapp.repository;

import com.springmusicapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

    Optional<T> findById(Long id);
    Optional<T> findByEmail(String email);
    List<T> findByName(String name);
}
