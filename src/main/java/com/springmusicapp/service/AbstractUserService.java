package com.springmusicapp.service;

import com.springmusicapp.exception.UserException;
import com.springmusicapp.model.User;
import com.springmusicapp.repository.UserRepository;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public abstract class AbstractUserService<T extends User> {

    protected final UserRepository<T> userRepository;

    public AbstractUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public T getByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("Entity not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    public T getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found with email: " + email, HttpStatus.NOT_FOUND));
    }

    public void create(T user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserException("This email is already used", HttpStatus.CONFLICT);
        }
        userRepository.save(user);
    }
}