package com.springmusicapp.domain.musician;

import com.springmusicapp.domain.band.model.Band;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class MusicianMapper {

    public static MusicianDTO toDto(Musician musician) {
        if (musician == null) return null;

        List<String> types = musician.getTypes().stream()
                .map(Enum::name)
                .toList();

        List<String> bandsName = musician
                .getBands().stream().map(Band::getName).toList();

        return new MusicianDTO(
                musician.getId(),
                musician.getName(),
                musician.getEmail(),
                musician.getStageName(),
                bandsName,
                types
        );
    }

    public static Musician toEntity(CreateMusicianDTO dto) {
        if (dto == null) {
            return null;
        }

        Musician musician = new Musician();
        musician.setStageName(dto.stageName());

        if (dto.types() != null) {
            EnumSet<MusicianType> typeSet = dto.types().stream()
                    .map(String::toUpperCase)
                    .map(MusicianType::valueOf)
                    .collect(Collectors.toCollection(() -> EnumSet.noneOf(MusicianType.class)));

            musician.setTypes(typeSet);
        } else {
            musician.setTypes(EnumSet.noneOf(MusicianType.class));
        }

        return musician;
    }
}
