package com.springmusicapp.service;

import com.springmusicapp.model.User;
import com.springmusicapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService<T extends User> {

    protected final UserRepository<T> repository;

    protected UserService(UserRepository<T> repository) {
        this.repository = repository;
    }

    public T findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email not found"));
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}