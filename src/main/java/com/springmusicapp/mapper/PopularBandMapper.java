package com.springmusicapp.mapper;

import com.springmusicapp.DTO.PopularBandDTO;
import com.springmusicapp.model.Musician;
import com.springmusicapp.model.PopularBand;
import org.springframework.stereotype.Component;

@Component
public class PopularBandMapper implements BandMapper<PopularBand, PopularBandDTO> {

    @Override
    public PopularBandDTO toDto(PopularBand popularBand) {
        PopularBandDTO dto = new PopularBandDTO();
        dto.setId(popularBand.getId());
        dto.setName(popularBand.getName());
        dto.setEarnedMoney(popularBand.getEarnedMoney());
        dto.setTotalSells(popularBand.getTotalSells());

        return dto;
    }

    public static PopularBand toEntity(PopularBandDTO dto) {
        PopularBand dtoEntity = new PopularBand();
        dtoEntity.setId(dto.getId());
        dtoEntity.setName(dto.getName());
        dtoEntity.setEarnedMoney(dto.getEarnedMoney());
        dtoEntity.setTotalSells(dto.getTotalSells());

        return dtoEntity;
    }
}
