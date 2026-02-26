package com.springmusicapp.mapper;

import com.springmusicapp.dto.CreateMusicianDTO;
import com.springmusicapp.dto.MusicianDTO;
import com.springmusicapp.model.Band;
import com.springmusicapp.model.Musician;
import com.springmusicapp.model.MusicianType;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MusicianMapper {

    public static MusicianDTO toDto(Musician musician) {
        if (musician == null) return null;

        MusicianDTO dto = new MusicianDTO();
        dto.setName(musician.getName());
        dto.setEmail(musician.getEmail());
        dto.setStageName(musician.getStageName());
        dto.setTypes(musician.getTypes().stream()
                .map(Enum::name)
                .toList());

        dto.setCurrentBand(
                Optional.ofNullable(musician.getCurrentBand())
                        .map(Band::getName)
                        .orElse(null)
        );

        return dto;
    }

    public static Musician toEntity(CreateMusicianDTO dto) {
        if (dto == null) {
            return null;
        }

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
