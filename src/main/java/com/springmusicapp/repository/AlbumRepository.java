package com.springmusicapp.repository;

import com.springmusicapp.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtist(String artistName);
    List<Album> findByAlbumName(String albumName);

}
