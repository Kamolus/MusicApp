package com.springmusicapp.dto;

import jakarta.validation.constraints.NotBlank;

public record LabelOwnerDTO(
        @NotBlank
        String name,

        @NotBlank
        String email,

        String phoneNumber,

        String position,

        @NotBlank
        String labelName
) {
}
