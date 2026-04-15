package com.springmusicapp.domain.label.dto.label_owner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateLabelOwnerDTO(
        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
        String email,

        String phoneNumber,

        LocalDate hireDate,

        String position
) {
}
