package com.springmusicapp.service;

import com.springmusicapp.model.User;
import com.springmusicapp.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public abstract class AbstractUserService<T extends User> {

    protected final UserRepository<T> userRepository;

    public AbstractUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<T> findByEmail(String email) {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public List<T> findByName(String name) {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getName().equalsIgnoreCase(name))
                .toList();
    }
}