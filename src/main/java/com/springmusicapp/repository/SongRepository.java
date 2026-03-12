package com.springmusicapp.repository;

import com.springmusicapp.model.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository {

    List<Song> findByArtist(String artist);
    List<Song> findByTitle(String title);
    List<Song> findByAlbum(String album);
}