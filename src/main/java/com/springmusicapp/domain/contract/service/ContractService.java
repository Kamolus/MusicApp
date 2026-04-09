package com.springmusicapp.domain.contract.service;

import com.springmusicapp.core.exception.BusinessLogicException;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.contract.dto.ContractDTO;
import com.springmusicapp.domain.contract.model.Contract;
import com.springmusicapp.domain.contract.model.ContractNegotiation;
import com.springmusicapp.domain.contract.repository.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Transactional
    public Contract createContractFromNegotiation(ContractNegotiation negotiation) {

        if (contractRepository.existsByBandIdAndIsActiveTrue(negotiation.getBand().getId())) {
            throw new BusinessLogicException("This band already has an active contract!","ERR_HAS_ACTIVE_CONTRACT");
        }

        Contract contract = new Contract();
        contract.setManager(negotiation.getManager());
        contract.setBand(negotiation.getBand());
        contract.setDuration(negotiation.getProposedDuration());
        contract.setBasePayment(negotiation.getProposedBasePayment());
        contract.setPayPerPerformance(negotiation.getProposedPayPerPerformance());

        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusMonths(negotiation.getProposedDuration()));
        contract.setActive(true);

        return contractRepository.save(contract);
    }

    public ContractDTO getActiveContractForBand(UUID bandId) {
        return contractRepository.findByBandIdAndIsActiveTrue(bandId)
                .orElseThrow(() -> new ResourceNotFoundException("Contract", "id", bandId));
    }

    public List<ContractDTO> getActiveContractsForManager(String managerId) {
        return contractRepository.findAllByManagerIdAndIsActiveTrue(managerId);
    }

    public Contract findById(UUID contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new ResourceNotFoundException("Contract","id", contractId));
    }
}
