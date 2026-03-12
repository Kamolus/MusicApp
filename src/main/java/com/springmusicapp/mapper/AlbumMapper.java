package com.springmusicapp.mapper;

import com.springmusicapp.dto.AlbumDTO;
import com.springmusicapp.dto.CreateAlbumDTO;
import com.springmusicapp.model.Album;
import com.springmusicapp.model.Song;

public class AlbumMapper {

    public static AlbumDTO toDto(Album album) {
        if (album == null) {
            return null;
        }

        AlbumDTO dto = new AlbumDTO();

        dto.setTitle(album.getTitle());
        dto.setGenre(album.getGenre().getName());
        dto.setSongTitles(album.getSongs().stream().map(Song::getTitle).toList());
        dto.setReleaseDate(album.getReleaseDate());
        return dto;
    }

    public static Album toEntity(CreateAlbumDTO dto) {
        if (dto == null) {
            return null;
        }

        Album album = new Album();
        album.setTitle(dto.getAlbumName());
        album.setReleaseDate(dto.getReleaseDate());

        return album;
    }
}
