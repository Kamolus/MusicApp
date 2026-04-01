package com.springmusicapp.domain.user.repository;

import com.springmusicapp.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserLoginRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
