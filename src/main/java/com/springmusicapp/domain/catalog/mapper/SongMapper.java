package com.springmusicapp.domain.catalog.mapper;

import com.springmusicapp.domain.catalog.dto.CreateSongDTO;
import com.springmusicapp.domain.catalog.dto.SongDTO;
import com.springmusicapp.domain.catalog.dto.SongForAlbumDTO;
import com.springmusicapp.domain.catalog.model.Song;

public class SongMapper {

    public static SongForAlbumDTO toDTO(Song song) {
        if (song == null) {
            return null;
        }

        return new SongForAlbumDTO(song.getId(), song.getTitle(), song.getViews(), song.getDuration());
    }

    public static SongDTO toFullDTO(Song song) {
        if (song == null) {
            return null;
        }

        String albumTitle = song.getAlbum() != null ? song.getAlbum().getTitle() : null;

        return new SongDTO(
                song.getId(),
                song.getTitle(),
                song.getDuration(),
                song.getViews(),
                song.getSpotifyId(),
                albumTitle
        );
    }

    public static Song toEntity(CreateSongDTO dto) {
        if (dto == null) {
            return null;
        }

        Song song = new Song();
        song.setTitle(dto.title());
        song.setDuration(dto.durationMs());

        return song;
    }
}
