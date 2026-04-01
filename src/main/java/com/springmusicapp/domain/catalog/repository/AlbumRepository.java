package com.springmusicapp.domain.catalog.repository;

import com.springmusicapp.domain.catalog.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AlbumRepository extends JpaRepository<Album, UUID> {

}
