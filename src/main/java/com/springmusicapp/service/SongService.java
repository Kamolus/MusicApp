package com.springmusicapp.service;

import com.springmusicapp.dto.CreateSongDTO;
import com.springmusicapp.dto.SongForAlbumDTO;
import com.springmusicapp.exception.ResourceNotFoundException;
import com.springmusicapp.model.Album;
import com.springmusicapp.model.Musician;
import com.springmusicapp.model.Song;
import com.springmusicapp.repository.AlbumRepository;
import com.springmusicapp.repository.MusicianRepository;
import com.springmusicapp.repository.SongRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public SongForAlbumDTO addSongToAlbum(Long albumId, CreateSongDTO createDto) {
       Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "id", albumId));

        Song song = new Song();
        song.setTitle(createDto.title());
        song.setDuration(createDto.durationMs());

        song.setAlbum(album);

        Song savedSong = songRepository.save(song);

        return new SongForAlbumDTO(savedSong.getTitle(), savedSong.getViews(), savedSong.getDuration());
    }

    public List<SongForAlbumDTO> getSongsSummaryForAlbum(Long albumId) {
       if (!albumRepository.existsById(albumId)) {
            throw new ResourceNotFoundException("Album", "id", albumId);
        }

        return songRepository.findSongByAlbumId(albumId);
    }

    @Transactional
    public void addStudioMusicianToSong(Long songId, Long musicianId) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song", "id", songId));

        Musician musician = musicianRepository.findById(musicianId)
                .orElseThrow(() -> new ResourceNotFoundException("Musician", "id", musicianId));

        song.getStudioMusicians().add(musician);

        songRepository.save(song);
    }
}
