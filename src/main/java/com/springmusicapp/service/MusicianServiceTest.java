package com.springmusicapp.service;

import com.springmusicapp.model.Musician;
import com.springmusicapp.model.PopularBand;
import com.springmusicapp.model.MusicianType;
import com.springmusicapp.model.UnpopularBand;
import com.springmusicapp.repository.MusicianRepository;
import com.springmusicapp.repository.PopularBandRepository;
import com.springmusicapp.repository.UnpopularBandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MusicianServiceTest {

    private MusicianRepository musicianRepository;
    private PopularBandRepository popularBandRepository;
    private UnpopularBandRepository unpopularBandRepository;
    private MusicianService musicianService;

    @BeforeEach
    void setUp() {
        musicianRepository = mock(MusicianRepository.class);
        popularBandRepository = mock(PopularBandRepository.class);
        unpopularBandRepository = mock(UnpopularBandRepository.class);

        musicianService = new MusicianService(musicianRepository, popularBandRepository, unpopularBandRepository);
    }

    @Test
    void shouldAssignMusicianToPopularBand() {
        Musician musician = new Musician("John", "john@example.com", "Johnny", EnumSet.of(MusicianType.VOCALIST));
        PopularBand band = new PopularBand("The Stars");

        when(musicianRepository.findById(1L)).thenReturn(Optional.of(musician));
        when(popularBandRepository.findById(2L)).thenReturn(Optional.of(band));

        musicianService.assignToBand(1L, 2L);

        assertEquals(band, musician.getCurrentBand());
        verify(musicianRepository).save(musician);
    }

    @Test
    void shouldAssignMusicianToUnpopularBand() {
        Musician musician = new Musician("Anna", "anna@example.com", "Annie", EnumSet.of(MusicianType.VOCALIST, MusicianType.DRUMMER));
        UnpopularBand band = new UnpopularBand("The Underground","students");

        when(musicianRepository.findById(1L)).thenReturn(Optional.of(musician));
        when(popularBandRepository.findById(2L)).thenReturn(Optional.empty());
        when(unpopularBandRepository.findById(2L)).thenReturn(Optional.of(band));

        musicianService.assignToBand(1L, 2L);

        assertEquals(band, musician.getCurrentBand());
        verify(musicianRepository).save(musician);
    }

    @Test
    void shouldThrowWhenMusicianNotAvailable() {
        Musician musician = new Musician("Tom", "tom@example.com", "Tommy", EnumSet.of(MusicianType.DRUMMER));
        musician.assignToBand(new PopularBand("Old Band"));

        when(musicianRepository.findById(1L)).thenReturn(Optional.of(musician));

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                musicianService.assignToBand(1L, 2L));

        assertTrue(ex.getMessage().contains("Musician is already in a band"));
    }

    @Test
    void shouldThrowWhenBandNotFound() {
        Musician musician = new Musician("Kate", "kate@example.com", "K", EnumSet.of(MusicianType.DRUMMER));

        when(musicianRepository.findById(1L)).thenReturn(Optional.of(musician));
        when(popularBandRepository.findById(2L)).thenReturn(Optional.empty());
        when(unpopularBandRepository.findById(2L)).thenReturn(Optional.empty());

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                musicianService.assignToBand(1L, 2L));

        assertTrue(ex.getMessage().contains("No band found"));
    }
}
