package com.springmusicapp.mapper;

import com.springmusicapp.DTO.MusicianDTO;
import com.springmusicapp.model.Musician;
import com.springmusicapp.model.MusicianType;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class MusicianMapper {

    public static MusicianDTO toDto(Musician musician) {
        if (musician == null) return null;

        MusicianDTO dto = new MusicianDTO();
        dto.setId(musician.getId());
        dto.setName(musician.getName());
        dto.setEmail(musician.getEmail());
        dto.setStageName(musician.getStageName());
        dto.setTypes(musician.getTypes().stream()
                .map(Enum::name)
                .toList());

        dto.setCurrentBand(musician.getCurrentBand() != null ? musician.getCurrentBand().getName() : null);

        return dto;
    }

    public static Musician toEntity(MusicianDTO dto) {
        if (dto == null) return null;

        Musician musician = new Musician();
        musician.setName(dto.getName());
        musician.setEmail(dto.getEmail());
        musician.setStageName(dto.getStageName());

        if (dto.getTypes() != null) {
            EnumSet<MusicianType> typeSet = dto.getTypes().stream()
                    .map(String::toUpperCase)
                    .map(MusicianType::valueOf)
                    .collect(Collectors.toCollection(() -> EnumSet.noneOf(MusicianType.class)));

            musician.setTypes(typeSet);
        } else {
            musician.setTypes(EnumSet.noneOf(MusicianType.class));
        }

        return musician;
    }

    public static EnumSet<MusicianType> mapTypes(List<String> typeStrings) {
        return typeStrings.stream()
                .map(MusicianType::valueOf)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(MusicianType.class)));
    }
}
