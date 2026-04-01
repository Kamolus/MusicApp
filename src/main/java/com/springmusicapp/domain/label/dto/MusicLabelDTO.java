package com.springmusicapp.domain.label.dto;

import java.util.List;
import java.util.UUID;

public record MusicLabelDTO(
        UUID id,
        String name,
        List<String> employeesNames,
        String taxNumber
) {
}
