package com.springmusicapp.domain.catalog.service;

import com.springmusicapp.domain.band.model.BandMembership;
import com.springmusicapp.domain.band.model.BandRole;
import com.springmusicapp.domain.catalog.dto.AlbumDTO;
import com.springmusicapp.domain.catalog.dto.CreateAlbumDTO;
import com.springmusicapp.core.exception.BusinessLogicException;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.catalog.mapper.AlbumMapper;
import com.springmusicapp.domain.catalog.model.Album;
import com.springmusicapp.domain.band.model.Band;
import com.springmusicapp.domain.catalog.repository.AlbumRepository;
import com.springmusicapp.domain.band.repository.BandRepository;
import com.springmusicapp.domain.catalog.repository.SongRepository;
import com.springmusicapp.domain.musician.MusicianRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.UUID;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final BandRepository bandRepository;
    private final SongRepository songRepository;
    private final MusicianRepository musicianRepository;


    public AlbumService(AlbumRepository albumRepository,
                        BandRepository bandRepository,
                        SongRepository songRepository,
                        MusicianRepository musicianRepository) {
        this.albumRepository = albumRepository;
        this.bandRepository = bandRepository;
        this.songRepository = songRepository;
        this.musicianRepository = musicianRepository;
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

    @Transactional
    public void deleteById(UUID id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserId = jwt.getSubject();

        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", id));

        Band band = album.getBand();

        BandMembership membership = band.getMemberships().stream()
                .filter(m -> m.getMusician().getId().equals(currentUserId))
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("You are not a member of this band"));

        if (membership.getRole() == BandRole.MEMBER) {
            throw new AccessDeniedException("Only Admins and Founders can delete albums");
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
