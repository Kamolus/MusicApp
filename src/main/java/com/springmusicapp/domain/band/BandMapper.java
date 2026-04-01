package com.springmusicapp.domain.band;


import com.springmusicapp.domain.catalog.model.Album;
import com.springmusicapp.domain.musician.Musician;

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
                band.getId(),
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
