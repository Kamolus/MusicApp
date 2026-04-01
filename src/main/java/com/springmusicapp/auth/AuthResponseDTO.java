package com.springmusicapp.auth;

public record AuthResponseDTO(String token, boolean requiresPasswordChange) {

    public AuthResponseDTO(String token) {
        this(token, false);
    }
}
