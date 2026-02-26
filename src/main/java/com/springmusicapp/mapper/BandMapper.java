package com.springmusicapp.mapper;


import com.springmusicapp.dto.BandDTO;
import com.springmusicapp.dto.CreateBandDTO;
import com.springmusicapp.model.Album;
import com.springmusicapp.model.Band;
import com.springmusicapp.model.Musician;


public class BandMapper{

    public static BandDTO toDto(Band band){
        if (band == null) {
            return null;
        }

        BandDTO dto = new BandDTO();

        dto.setName(band.getName());
        dto.setBandStatus(band.getStatus());
        dto.setAlbumsTitles(band.getAlbums().stream()
                .map(Album::getTitle).toList());
        dto.setMusiciansName(band.getMembers().stream()
                .map(Musician::getStageName).toList());
        dto.setHasActiveContract(band.getContract() != null);
        return dto;
    }

    public static Band toEntity(CreateBandDTO createDto) {
        if (createDto == null) {
            return null;
        }

        Band band = new Band();
        band.setName(createDto.getName());

        return band;
    }
}
