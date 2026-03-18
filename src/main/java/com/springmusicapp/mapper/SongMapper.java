package com.springmusicapp.mapper;

import com.springmusicapp.dto.CreateSongDTO;
import com.springmusicapp.dto.SongForAlbumDTO;
import com.springmusicapp.model.Song;

public class SongMapper {

    public static SongForAlbumDTO toDTO(Song song) {
        if (song == null) {
            return null;
        }

        return new SongForAlbumDTO(song.getTitle(), song.getViews(), song.getDuration());
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
