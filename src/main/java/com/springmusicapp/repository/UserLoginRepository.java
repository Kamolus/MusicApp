package com.springmusicapp.repository;

import com.springmusicapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
