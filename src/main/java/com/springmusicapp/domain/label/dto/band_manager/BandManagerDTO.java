package com.springmusicapp.domain.label.dto.band_manager;

import com.springmusicapp.domain.contract.dto.ManagerContractDTO;

import java.util.List;

public record BandManagerDTO(
        String id,
        String name,
        String email,
        String phoneNumber,
        List<ManagerContractDTO> contracts
) {
}
