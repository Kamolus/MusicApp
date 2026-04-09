package com.springmusicapp.domain.contract.controller;


import com.springmusicapp.domain.contract.dto.ContractDTO;
import com.springmusicapp.domain.contract.mapper.ContractMapper;
import com.springmusicapp.domain.contract.model.Contract;
import com.springmusicapp.domain.contract.service.ContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> getContract(@PathVariable UUID id) {
        Contract contract = contractService.findById(id);

        return ResponseEntity.ok(ContractMapper.toDTO(contract));
    }

    @GetMapping("/band/{bandId}")
    public ResponseEntity<ContractDTO> getActiveContractForBand(@PathVariable UUID bandId) {
        ContractDTO contract = contractService.getActiveContractForBand(bandId);
        return ResponseEntity.ok(contract);
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<ContractDTO>> getActiveContractsForManager(@PathVariable String managerId) {
        List<ContractDTO> contracts = contractService.getActiveContractsForManager(managerId);
        return ResponseEntity.ok(contracts);
    }
}
