package com.springmusicapp.domain.catalog.service;

import com.springmusicapp.domain.catalog.dto.CreateSongDTO;
import com.springmusicapp.domain.catalog.dto.SongDTO;
import com.springmusicapp.domain.catalog.dto.SongForAlbumDTO;
import com.springmusicapp.core.exception.ResourceNotFoundException;
import com.springmusicapp.domain.catalog.mapper.SongMapper;
import com.springmusicapp.domain.catalog.model.Album;
import com.springmusicapp.domain.musician.Musician;
import com.springmusicapp.domain.catalog.model.Song;
import com.springmusicapp.domain.catalog.repository.AlbumRepository;
import com.springmusicapp.domain.musician.MusicianRepository;
import com.springmusicapp.domain.catalog.repository.SongRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final MusicianRepository musicianRepository;

    public SongService(SongRepository songRepository,
                       AlbumRepository albumRepository,
                       MusicianRepository musicianRepository) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.musicianRepository = musicianRepository;
    }

    @Transactional
    public SongForAlbumDTO addSongToAlbum(UUID albumId, CreateSongDTO createDto) {
       Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumId));

        Song song = new Song();
        song.setTitle(createDto.title());
        song.setDuration(createDto.durationMs());

        song.setAlbum(album);

        Song savedSong = songRepository.save(song);

        return new SongForAlbumDTO(savedSong.getId(), savedSong.getTitle(), savedSong.getViews(), savedSong.getDuration());
    }

    public List<SongForAlbumDTO> getSongsSummaryForAlbum(UUID albumId) {
       if (!albumRepository.existsById(albumId)) {
            throw new ResourceNotFoundException("Album", "id", albumId);
        }

        return songRepository.findSongByAlbumId(albumId);
    }

    @Transactional
    public void addStudioMusicianToSong(UUID songId, String musicianId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song", "id", songId));

        Musician musician = musicianRepository.findById(musicianId)
                .orElseThrow(() -> new ResourceNotFoundException("Musician", "id", musicianId));

        song.getStudioMusicians().add(musician);

        songRepository.save(song);
    }

    public List<SongDTO> findAllSongs() {
        return songRepository.findAll().stream()
                .map(SongMapper::toFullDTO)
                .toList();
    }

    public SongDTO findSongById(UUID id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song", "id", id));
        return SongMapper.toFullDTO(song);
    }

    @Transactional
    public SongDTO updateSong(UUID id, CreateSongDTO updateDto) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song", "id", id));

        song.setTitle(updateDto.title());
        song.setDuration(updateDto.durationMs());
        Song updatedSong = songRepository.save(song);
        return SongMapper.toFullDTO(updatedSong);
    }

    @Transactional
    public void deleteSong(UUID id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String currentUserId = jwt.getSubject();

        Musician currentMusician = musicianRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Musician", "id", currentUserId));

        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song", "id", id));

        if (currentMusician.getCurrentBand() == null ||
                !song.getAlbum().getBand().getId().equals(currentMusician.getCurrentBand().getId())) {

            throw new AccessDeniedException("You are not authorized to remove this song");
        }
        songRepository.deleteById(id);
    }
}
