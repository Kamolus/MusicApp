package com.springmusicapp.domain.contract.dto;

import jakarta.validation.constraints.Min;

public record CounterOfferDTO(
        @Min(value = 1, message = "Duration cannot be less than 1")
        int proposedDurationInMonths,

        @Min(value = 0, message = "Base payment cannot be less than 0")
        double proposedBasePayment,

        @Min(value = 0, message = "Pay per performance cannot be less than 0")
        double proposedPayPerPerformance
) {}
