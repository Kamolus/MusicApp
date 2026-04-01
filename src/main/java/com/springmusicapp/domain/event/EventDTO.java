package com.springmusicapp.domain.event;

import java.time.LocalDate;
import java.util.UUID;

public record EventDTO(
        UUID id,
        String name,
        LocalDate date
) {
}
