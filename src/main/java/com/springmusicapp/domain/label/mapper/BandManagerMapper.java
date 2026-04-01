package com.springmusicapp.domain.label.mapper;

import com.springmusicapp.domain.label.dto.BandManagerDTO;
import com.springmusicapp.domain.label.dto.CreateBandManagerDTO;
import com.springmusicapp.domain.contract.dto.ManagerContractDTO;
import com.springmusicapp.domain.label.model.BandManager;

import java.util.List;
import java.util.stream.Collectors;

public class BandManagerMapper {

    public static BandManagerDTO toDTO(BandManager bandManager) {
        if (bandManager == null) return null;

        List<ManagerContractDTO> contracts = bandManager.getContracts() != null
                ? bandManager.getContracts()
                .stream()
                .map(contract -> new ManagerContractDTO(
                                contract.getBand().getName(),
                                contract.getDuration()))
                .collect(Collectors.toList()) : List.of();

        return new BandManagerDTO(
                bandManager.getId(),
                bandManager.getName(),
                bandManager.getEmail(),
                bandManager.getPhoneNumber(),
                contracts
        );
    }

    public static BandManager toEntity(CreateBandManagerDTO dto) {
        if (dto == null) return null;

        BandManager bandManager = new BandManager();
        bandManager.setName(dto.name());
        bandManager.setEmail(dto.email());
        bandManager.setPhoneNumber(dto.phoneNumber());

        if(dto.hireDate() != null) {
            bandManager.setHireDate(dto.hireDate());
        }

        return bandManager;
    }
}
