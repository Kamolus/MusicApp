package com.springmusicapp.domain.musician;

import com.springmusicapp.domain.band.Band;
import com.springmusicapp.domain.user.model.Role;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MusicianMapper {

    public static MusicianDTO toDto(Musician musician) {
        if (musician == null) return null;

        List<String> types = musician.getTypes().stream()
                .map(Enum::name)
                .toList();

        String currentBandName = Optional.ofNullable(musician.getCurrentBand())
                .map(Band::getName)
                .orElse(null);

        return new MusicianDTO(
                musician.getId(),
                musician.getName(),
                musician.getEmail(),
                musician.getStageName(),
                currentBandName,
                types
        );
    }

    public static Musician toEntity(CreateMusicianDTO dto) {
        if (dto == null) {
            return null;
        }

        Musician musician = new Musician();
        musician.setName(dto.name());
        musician.setEmail(dto.email());
        musician.setStageName(dto.stageName());
        musician.setPassword(dto.password());
        musician.setRole(Role.ROLE_MUSICIAN);

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
