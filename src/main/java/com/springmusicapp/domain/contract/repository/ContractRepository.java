package com.springmusicapp.domain.contract.repository;

import com.springmusicapp.domain.contract.dto.ContractDTO;
import com.springmusicapp.domain.contract.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {

    boolean existsByBandIdAndIsActiveTrue(UUID bandId);

    Optional<ContractDTO> findByBandIdAndIsActiveTrue(UUID bandId);

    List<ContractDTO> findAllByManagerIdAndIsActiveTrue(String managerId);

    Optional<Contract> findById(UUID contractId);
}
