package com.springmusicapp.domain.contract.mapper;

import com.springmusicapp.domain.contract.dto.ContractDTO;
import com.springmusicapp.domain.contract.model.Contract;

public class ContractMapper {

    public static ContractDTO toDTO(Contract contract) {
        if (contract == null) return null;

        ContractDTO.ManagerSummaryDTO managerSummaryDTO = new ContractDTO.ManagerSummaryDTO(
                contract.getManager().getId(),
                contract.getManager().getName()
        );

        ContractDTO.BandSummaryDTO bandSummaryDTO = new ContractDTO.BandSummaryDTO(
                contract.getBand().getId(),
                contract.getBand().getName()
        );

        return new ContractDTO(
                contract.getId(),
                managerSummaryDTO,
                bandSummaryDTO,
                contract.getDuration(),
                contract.getBasePayment(),
                contract.getPayPerPerformance()
        );
    }
}
