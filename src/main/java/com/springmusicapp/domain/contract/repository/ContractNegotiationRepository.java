package com.springmusicapp.domain.contract.repository;

import com.springmusicapp.domain.contract.model.ContractNegotiation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContractNegotiationRepository extends JpaRepository<ContractNegotiation, UUID> {
    List<ContractNegotiation> findAllByManagerId(String managerId);
    List<ContractNegotiation> findAllByBandId(UUID bandId);
}
