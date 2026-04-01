package com.springmusicapp.domain.musician;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateMusicianDTO(

        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 4, message = "Password must be at least 4 characters long")
        String password,

        @NotBlank(message = "Stage name cannot be empty")
        String stageName,

        @NotEmpty(message = "Types cannot be empty")
        Set<String> types
) {
}
