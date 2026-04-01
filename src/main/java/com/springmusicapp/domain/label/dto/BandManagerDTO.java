package com.springmusicapp.domain.label.dto;

import com.springmusicapp.domain.contract.dto.ManagerContractDTO;

import java.util.List;
import java.util.UUID;

public record BandManagerDTO(
        UUID id,
        String name,
        String email,
        String phoneNumber,
        List<ManagerContractDTO> contracts
) {
}
