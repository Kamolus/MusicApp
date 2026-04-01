package com.springmusicapp.domain.label.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record LabelOwnerDTO(
        UUID id,
        String name,
        String email,
        String phoneNumber,
        String position,
        String labelName
) {
}
