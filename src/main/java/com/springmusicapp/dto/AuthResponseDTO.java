package com.springmusicapp.dto;

public record AuthResponseDTO(String token, boolean requiresPasswordChange) {

    public AuthResponseDTO(String token) {
        this(token, false);
    }
}
