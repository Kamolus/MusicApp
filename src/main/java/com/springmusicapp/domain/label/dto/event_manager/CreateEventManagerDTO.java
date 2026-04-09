package com.springmusicapp.domain.label.dto.event_manager;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateEventManagerDTO(
        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
        String email,

        String areaOfOperation,

        LocalDate hireDate
) {
}
