package com.springmusicapp.domain.label.dto.band_manager;

import com.springmusicapp.core.base.RegisterTemplate;

public record RegisterBandManagerDTO(
        String id,
        String email,
        String name,
        CreateBandManagerDTO userInput
) implements RegisterTemplate<CreateBandManagerDTO> {
}
