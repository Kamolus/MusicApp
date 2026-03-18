package com.springmusicapp.repository;

import com.springmusicapp.dto.SongForAlbumDTO;
import com.springmusicapp.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {


    @Query("SELECT new com.springmusicapp.dto.SongForAlbumDTO(s.title, s.views, s.duration) FROM Song s WHERE s.album.id = :albumId")
    List<SongForAlbumDTO> findSongByAlbumId(@Param("albumId") Long albumId);
}