package com.springmusicapp.domain.contract.dto;

import com.springmusicapp.core.base.InvitationStatus;

import java.util.UUID;

public record ContractNegotiationDTO(
        UUID id,
        UUID managerId,
        UUID bandId,
        int proposedDuration,
        double proposedBasePayment,
        double proposedPayPerPerformance,
        InvitationStatus status
) {}
