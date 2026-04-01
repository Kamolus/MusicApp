package com.springmusicapp.domain.catalog.repository;

import com.springmusicapp.domain.catalog.dto.SongForAlbumDTO;
import com.springmusicapp.domain.catalog.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SongRepository extends JpaRepository<Song, UUID> {


    @Query("SELECT new com.springmusicapp.domain.catalog.dto.SongForAlbumDTO(s.id ,s.title, s.views, s.duration) FROM Song s WHERE s.album.id = :albumId")
    List<SongForAlbumDTO> findSongByAlbumId(@Param("albumId") UUID albumId);
}