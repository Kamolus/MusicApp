package com.springmusicapp.repository;

import com.springmusicapp.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

    Optional<T> findByEmail(String email);
    List<T> findByName(String name);
    void deleteByEmail(String email);
    boolean existsByEmail(@Email @NotBlank String email);
}
