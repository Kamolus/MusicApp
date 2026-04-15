package com.springmusicapp.domain.label.dto.label_owner;

import com.springmusicapp.core.base.RegisterTemplate;

public record RegisterLabelOwnerDTO(
        String id,
        String email,
        String name,
        CreateLabelOwnerDTO userInput
) implements RegisterTemplate<CreateLabelOwnerDTO> {
}
