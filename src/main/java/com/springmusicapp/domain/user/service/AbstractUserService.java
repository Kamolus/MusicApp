package com.springmusicapp.domain.user.service;

import com.springmusicapp.core.exception.BusinessLogicException;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.user.model.User;
import com.springmusicapp.domain.user.repository.UserRepository;

import java.util.UUID;

public abstract class AbstractUserService<T extends User> {

    protected final UserRepository<T> userRepository;

    public AbstractUserService(UserRepository<T> userRepository) {
        this.userRepository = userRepository;
    }

    public T getByIdOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id", "id", id));
    }

    public T getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    public T create(T user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessLogicException("This email is already used", "ERR_EMAIL_ALREADY_USED");
        }
        return userRepository.save(user);
    }
}