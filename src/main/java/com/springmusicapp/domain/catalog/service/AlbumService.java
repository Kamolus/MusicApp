package com.springmusicapp.domain.catalog.service;

import com.springmusicapp.domain.catalog.dto.AlbumDTO;
import com.springmusicapp.domain.catalog.dto.CreateAlbumDTO;
import com.springmusicapp.core.exception.BusinessLogicException;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.catalog.mapper.AlbumMapper;
import com.springmusicapp.domain.catalog.model.Album;
import com.springmusicapp.domain.band.Band;
import com.springmusicapp.domain.catalog.repository.AlbumRepository;
import com.springmusicapp.domain.band.BandRepository;
import com.springmusicapp.domain.catalog.repository.SongRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AlbumService {

    protected final AlbumRepository albumRepository;
    protected final BandRepository bandRepository;
    protected final SongRepository songRepository;


    public AlbumService(AlbumRepository albumRepository,
                        BandRepository bandRepository,
                        SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.bandRepository = bandRepository;
        this.songRepository = songRepository;
    }

    public AlbumDTO findById(UUID id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Album", "id", id));
        return AlbumMapper.toDto(album);
    }

    public List<AlbumDTO> findAll() {
        return albumRepository.findAll().stream().map(AlbumMapper::toDto).toList();
    }

    @Transactional
    public AlbumDTO createAlbumForBand(UUID bandId, CreateAlbumDTO createDto) {
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new ResourceNotFoundException("Band", "id", bandId));

        Album album = AlbumMapper.toEntity(createDto);

        album.setBand(band);
        Album savedAlbum = albumRepository.save(album);

        return AlbumMapper.toDto(savedAlbum);
    }

    public void deleteById(UUID id) {
        if (!albumRepository.existsById(id)) {
            throw new ResourceNotFoundException("Album", "id", id);
        }
        albumRepository.deleteById(id);
    }

    @Transactional
    public AlbumDTO assignAlbumToBand(UUID albumId, UUID bandId) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumId));
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new ResourceNotFoundException("Band", "id", bandId));

        if(album.getBand() != null && album.getBand().getId().equals(band.getId())) {
            throw new BusinessLogicException("Album is already assigned to this band",
                    "ERR_ALBUM_ALREADY_ASSIGNED_TO_BAND");
        }

        album.setBand(band);
        Album savedAlbum = albumRepository.save(album);
        return AlbumMapper.toDto(savedAlbum);
    }
}
