package com.springmusicapp.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record MusicLabelDTO(
        @NotBlank
        String name,

        List<String> employeesNames,

        @NotBlank
        String taxNumber
) {
}
