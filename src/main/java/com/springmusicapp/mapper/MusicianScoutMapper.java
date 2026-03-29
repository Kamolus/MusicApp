package com.springmusicapp.mapper;

import com.springmusicapp.dto.CreateMusicianScoutDTO;
import com.springmusicapp.dto.MusicianScoutDTO;
import com.springmusicapp.model.MusicianScout;

public class MusicianScoutMapper {

    public static MusicianScoutDTO toDto(MusicianScout musicianScout) {
        if (musicianScout == null) return null;

        return new MusicianScoutDTO(
                musicianScout.getName(),
                musicianScout.getEmail(),
                musicianScout.getPhoneNumber()
        );
    }

    public static MusicianScout toEntity(CreateMusicianScoutDTO dto) {
        if (dto == null) return null;

        MusicianScout musicianScout = new MusicianScout();
        musicianScout.setName(dto.name());
        musicianScout.setEmail(dto.email());
        musicianScout.setPassword(dto.password());
        musicianScout.setPhoneNumber(dto.phoneNumber());

        if(dto.hireDate() != null) {
            musicianScout.setHireDate(dto.hireDate());
        }

        return musicianScout;
    }
}
