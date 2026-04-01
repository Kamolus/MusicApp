package com.springmusicapp.domain.catalog.mapper;

import com.springmusicapp.domain.catalog.dto.AlbumDTO;
import com.springmusicapp.domain.catalog.dto.CreateAlbumDTO;
import com.springmusicapp.domain.catalog.dto.SongForAlbumDTO;
import com.springmusicapp.domain.catalog.model.Album;

import java.util.List;

public class AlbumMapper {

    public static AlbumDTO toDto(Album album) {
        if (album == null) {
            return null;
        }

        List<SongForAlbumDTO> songDtos = album.getSongs().stream()
                .map(song -> new SongForAlbumDTO(song.getId(), song.getTitle(), song.getViews(), song.getDuration()))
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
