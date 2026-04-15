package com.springmusicapp.domain.user.service;

import com.springmusicapp.core.base.UserDeletedEvent;
import com.springmusicapp.core.exception.BusinessLogicException;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.user.model.User;
import com.springmusicapp.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.event.EventListener;

public abstract class AbstractUserService<T extends User> {

    protected final UserRepository<T> userRepository;

    public AbstractUserService(UserRepository<T> userRepository) {
        this.userRepository = userRepository;
    }

    public T getByIdOrThrow(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id", "id", id));
    }

    public T getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    public T create(T user, String keycloakId, String email, String name) {
        if (userRepository.existsById(keycloakId)) {
            throw new BusinessLogicException("User already existed", "ERR_PROFILE_EXISTS");
        }

        if (userRepository.existsByEmail(email)) {
            throw new BusinessLogicException("This email is already used", "ERR_EMAIL_ALREADY_USED");
        }

        user.setId(keycloakId);
        user.setEmail(email);
        user.setName(name);

        return userRepository.save(user);
    }

    @EventListener
    @Transactional
    public void handleUserDeletedEvent(UserDeletedEvent event) {
        String id = event.id();
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }
}