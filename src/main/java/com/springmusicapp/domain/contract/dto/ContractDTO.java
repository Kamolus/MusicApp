package com.springmusicapp.domain.contract.dto;

import java.util.UUID;

public record ContractDTO(
        UUID id,
        ManagerSummaryDTO manager,
        BandSummaryDTO band,
        int duration,
        double basePayment,
        double payPerPerformance
) {
    public record ManagerSummaryDTO(String id, String name) {}
    public record BandSummaryDTO(UUID id, String name) {}
}

