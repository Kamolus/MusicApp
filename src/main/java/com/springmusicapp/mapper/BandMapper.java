package com.springmusicapp.mapper;


import com.springmusicapp.dto.BandDTO;
import com.springmusicapp.dto.CreateBandDTO;
import com.springmusicapp.model.Album;
import com.springmusicapp.model.Band;
import com.springmusicapp.model.Musician;

import java.util.List;


public class BandMapper{

    public static BandDTO toDto(Band band){
        if (band == null) {
            return null;
        }
        List<String> albumsTitles = band.getAlbums().stream()
                .map(Album::getTitle).toList();

        List<String> musiciansNames = band.getMembers().stream()
                .map(Musician::getStageName).toList();

        return new BandDTO(
                band.getName(),
                musiciansNames,
                albumsTitles
        );
    }

    public static Band toEntity(CreateBandDTO createDto) {
        if (createDto == null) {
            return null;
        }

        Band band = new Band();
        band.setName(createDto.name());

        return band;
    }
}
