package com.springmusicapp.mapper;

import com.springmusicapp.dto.AlbumDTO;
import com.springmusicapp.dto.CreateAlbumDTO;
import com.springmusicapp.dto.SongForAlbumDTO;
import com.springmusicapp.model.Album;
import com.springmusicapp.model.Song;

import java.util.List;

public class AlbumMapper {

    public static AlbumDTO toDto(Album album) {
        if (album == null) {
            return null;
        }

        List<SongForAlbumDTO> songDtos = album.getSongs().stream()
                .map(song -> new SongForAlbumDTO(song.getTitle(), song.getViews(), song.getDuration()))
                .toList();

        return new AlbumDTO(
                album.getId(),
                album.getTitle(),
                album.getReleaseDate(),
                album.getBand().getName(),
                album.getImageUrl(),
                songDtos
        );
    }

    public static Album toEntity(CreateAlbumDTO dto) {
        if (dto == null) {
            return null;
        }

        Album album = new Album();
        album.setTitle(dto.title());
        album.setReleaseDate(dto.releaseDate());
        album.setImageUrl(dto.imageUrl());

        return album;
    }
}
