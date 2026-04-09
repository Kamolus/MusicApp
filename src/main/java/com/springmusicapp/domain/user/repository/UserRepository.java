package com.springmusicapp.domain.user.repository;

import com.springmusicapp.domain.user.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, String> {

    Optional<T> findByEmail(String email);
    List<T> findByName(String name);
    void deleteByEmail(String email);
    boolean existsByEmail(@Email @NotBlank String email);
}
