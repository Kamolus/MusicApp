package com.springmusicapp.core.config;

import com.springmusicapp.domain.band.Band;
import com.springmusicapp.domain.band.BandRepository;
import com.springmusicapp.domain.catalog.model.Album;
import com.springmusicapp.domain.catalog.model.Song;
import com.springmusicapp.domain.catalog.repository.AlbumRepository;
import com.springmusicapp.domain.catalog.repository.SongRepository;
import com.springmusicapp.domain.label.model.BandManager;
import com.springmusicapp.domain.label.repository.BandManagerRepository;
import com.springmusicapp.domain.musician.Musician;
import com.springmusicapp.domain.musician.MusicianController;
import com.springmusicapp.domain.musician.MusicianRepository;
import com.springmusicapp.domain.musician.MusicianType;
import com.springmusicapp.domain.user.model.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final BandManagerRepository managerRepository;
    private final MusicianRepository musicianRepository;
    private final BandRepository bandRepository;
    private final PasswordEncoder passwordEncoder;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    public DatabaseSeeder(
            BandManagerRepository managerRepository,
            MusicianRepository musicianRepository,
            BandRepository bandRepository,
            PasswordEncoder passwordEncoder, AlbumRepository albumRepository, SongRepository songRepository) {
        this.managerRepository = managerRepository;
        this.musicianRepository = musicianRepository;
        this.bandRepository = bandRepository;
        this.passwordEncoder = passwordEncoder;
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (managerRepository.count() == 0 && musicianRepository.count() == 0) {

            BandManager manager = new BandManager();
            manager.setEmail("manager@test.com");
            manager.setPassword(passwordEncoder.encode("haslo123"));
            manager.setName("Jan Kowalski (Manager)");
            manager.setRole(Role.ROLE_BAND_MANAGER);
            managerRepository.save(manager);

            Band band = new Band();
            band.setName("The Springers");
            band = bandRepository.save(band);

            Album album = new Album();
            album.setTitle("HHHH");
            album.setReleaseDate("1933-01-11");
            band.addAlbum(album);
            album = albumRepository.save(album);
            Song song = new Song();
            song.setTitle("ss");
            song.setViews(2000000);
            song.setAlbum(album);
            song = songRepository.save(song);

            Musician musician = new Musician();
            musician.setEmail("muzyk@test.com");
            musician.setPassword(passwordEncoder.encode("haslo123"));
            musician.setName("Piotr Nowak");
            musician.setRole(Role.ROLE_MUSICIAN);
            Set<MusicianType> types = new HashSet<>();
            types.add(MusicianType.GUITARIST);
            musician.setTypes(types);
            musician.setStageName("Piotr Nowak");
            musician.setCurrentBand(band);
            musicianRepository.save(musician);

            Musician musician2 = new Musician();
            musician2.setEmail("muzyk2@test.com");
            musician2.setPassword(passwordEncoder.encode("haslo123"));
            musician2.setName("Adam Nowak");
            musician2.setRole(Role.ROLE_MUSICIAN);
            musician2.setTypes(types);
            musician2.setStageName("Pssa");
            musicianRepository.save(musician2);

            System.out.println("Dane przykładowe zostały wczytane pomyślnie!");
            System.out.println("Zaloguj się jako: manager@test.com / haslo123");
            System.out.println("Zaloguj się jako: muzyk@test.com / haslo123");
            System.out.println("ID Zespołu (Band ID) do testów: " + band.getId());
        } else {
            System.out.println("Baza danych zawiera już wpisy. Pomijam wczytywanie danych.");
        }
    }
}