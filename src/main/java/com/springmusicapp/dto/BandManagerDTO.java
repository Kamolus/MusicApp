package com.springmusicapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record BandManagerDTO(
        @NotBlank
        String name,

        @NotBlank
        String email,

        String phoneNumber,

        List<ManagerContractDTO> contracts
) {
}
