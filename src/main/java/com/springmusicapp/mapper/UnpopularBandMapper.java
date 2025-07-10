package com.springmusicapp.mapper;


import com.springmusicapp.DTO.UnpopularBandDTO;
import com.springmusicapp.model.UnpopularBand;
import org.springframework.stereotype.Component;

@Component
public class UnpopularBandMapper implements BandMapper<UnpopularBand, UnpopularBandDTO> {

    @Override
    public UnpopularBandDTO toDto(UnpopularBand unpopularBand) {
        if (unpopularBand == null) return null;

        UnpopularBandDTO dto = new UnpopularBandDTO();
        dto.setId(unpopularBand.getId());
        dto.setName(unpopularBand.getName());
        dto.setTargetGroup(unpopularBand.getTargetGroup());
        dto.setTotalSells(unpopularBand.getTotalSells());

        return dto;
    }

    public static UnpopularBand toEntity(UnpopularBandDTO dto) {
        if (dto == null) return null;

        UnpopularBand dtoEntity = new UnpopularBand();
        dtoEntity.setId(dto.getId());
        dtoEntity.setName(dto.getName());
        dtoEntity.setTargetGroup(dto.getTargetGroup());
        dtoEntity.setTotalSells(dto.getTotalSells());

        return dtoEntity;
    }
}
