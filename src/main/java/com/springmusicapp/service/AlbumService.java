package com.springmusicapp.service;

import com.springmusicapp.dto.AlbumDTO;
import com.springmusicapp.dto.CreateAlbumDTO;
import com.springmusicapp.exception.BusinessLogicException;
import com.springmusicapp.exception.ResourceNotFoundException;
import com.springmusicapp.mapper.AlbumMapper;
import com.springmusicapp.model.Album;
import com.springmusicapp.model.Band;
import com.springmusicapp.repository.AlbumRepository;
import com.springmusicapp.repository.BandRepository;
import com.springmusicapp.repository.SongRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public AlbumDTO findById(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Album", "id", id));
        return AlbumMapper.toDto(album);
    }

    public List<AlbumDTO> findAll() {
        return albumRepository.findAll().stream().map(AlbumMapper::toDto).toList();
    }

    @Transactional
    public AlbumDTO createAlbumForBand(Long bandId, CreateAlbumDTO createDto) {
        Band band = bandRepository.findById(bandId)
                .orElseThrow(() -> new ResourceNotFoundException("Band", "id", bandId));

        Album album = AlbumMapper.toEntity(createDto);

        album.setBand(band);
        Album savedAlbum = albumRepository.save(album);

        return AlbumMapper.toDto(savedAlbum);
    }

    public void deleteById(Long id) {
        if (!albumRepository.existsById(id)) {
            throw new ResourceNotFoundException("Album", "id", id);
        }
        albumRepository.deleteById(id);
    }

    @Transactional
    public AlbumDTO assignAlbumToBand(Long albumId, Long bandId) {
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
