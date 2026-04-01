package com.springmusicapp.domain.contract.controller;

import com.springmusicapp.domain.contract.dto.ContractDTO;
import com.springmusicapp.domain.contract.dto.ContractNegotiationDTO;
import com.springmusicapp.domain.contract.dto.CounterOfferDTO;
import com.springmusicapp.domain.contract.dto.CreateContractDTO;
import com.springmusicapp.domain.contract.service.ContractNegotiationService;
import com.springmusicapp.domain.user.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/contracts/negotiations")
public class ContractNegotiationController {

    private final ContractNegotiationService negotiationService;

    public ContractNegotiationController(ContractNegotiationService negotiationService) {
        this.negotiationService = negotiationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ContractNegotiationDTO> proposeContract(
            @Valid @RequestBody CreateContractDTO requestDto,
            @AuthenticationPrincipal User loggedInUser
    ) {
        ContractNegotiationDTO created = negotiationService.createOffer(requestDto, loggedInUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{id}/counter")
    @PreAuthorize("hasRole('MUSICIAN')")
    public ResponseEntity<ContractNegotiationDTO> makeCounterOffer(
            @PathVariable("id") UUID negotiationId,
            @Valid @RequestBody CounterOfferDTO requestDto,
            @AuthenticationPrincipal User loggedInUser
    ) {
        ContractNegotiationDTO updated = negotiationService.counterOfferByBand(negotiationId, requestDto, loggedInUser.getId());
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<ContractDTO> acceptNegotiation(@PathVariable("id") UUID negotiationId) {
        ContractDTO finalContract = negotiationService.acceptNegotiation(negotiationId);
        return ResponseEntity.ok(finalContract);
    }
}
