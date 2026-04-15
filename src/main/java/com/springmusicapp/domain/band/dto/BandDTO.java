package com.springmusicapp.domain.band.dto;

import java.util.List;
import java.util.UUID;

public record BandDTO(
        UUID bandId,
        String name,
        List<String> musiciansName,
        List<String> albumsTitles
) {
}
