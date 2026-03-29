package com.springmusicapp.mapper;

import com.springmusicapp.dto.CreateLabelOwnerDTO;
import com.springmusicapp.dto.LabelOwnerDTO;
import com.springmusicapp.model.LabelOwner;

public class LabelOwnerMapper {

    public static LabelOwnerDTO toDTO(LabelOwner labelOwner) {
        if (labelOwner == null) return null;

        String labelName = labelOwner.getMusicLabel().getName();


        return new LabelOwnerDTO(
                labelOwner.getName(),
                labelOwner.getEmail(),
                labelOwner.getPhoneNumber(),
                labelOwner.getPosition(),
                labelName
        );
    }

    public static LabelOwner toEntity(CreateLabelOwnerDTO dto) {
        if (dto == null) return null;

        LabelOwner labelOwner = new LabelOwner();
        labelOwner.setName(dto.name());
        labelOwner.setEmail(dto.email());
        labelOwner.setPhoneNumber(dto.phoneNumber());
        labelOwner.setPosition(dto.position());
        labelOwner.setHireDate(dto.hireDate());

        return labelOwner;
    }
}
