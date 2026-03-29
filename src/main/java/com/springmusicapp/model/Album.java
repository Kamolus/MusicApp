package com.springmusicapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Klasa reprezentująca album muzyczny.
 * Obsługuje relacje z zespołem, gatunkiem i utworami.
 */
@Entity
@Getter
@Setter
@Table(name = "albums")
public class Album{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String spotifyId;

    private String imageUrl;

    @NotBlank
    private String title;

    @Column(nullable = false)
    @DateTimeFormat
    private String releaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Genre genre;

    @ManyToOne(optional = false)
    @JoinColumn(name = "band_id", nullable = false)
    private Band band;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs = new ArrayList<>();

    public Album(String title, String releaseDate, Genre genre) {
        setTitle(title);
        setReleaseDate(releaseDate);
        setGenre(genre);
    }

    public Album(){}

    public void setGenre(Genre genre) {
        if (this.genre == null || genre.equals(this.genre)) {
            this.genre = genre;
            genre.addAlbum(this);
        }
    }

    public void removeGenre(Genre genre) {
        if (genre.equals(this.genre)) {
            this.genre = null;
            genre.removeAlbum(this);
        }
    }


    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Album title cannot be empty");
        }
        this.title = title;
    }


    public void setBand(Band band) {
        if (band != null) {
            this.band = band;
            band.addAlbum(this);
        }
    }

    public int getTotalViews() {
        return songs.stream().mapToInt(Song::getViews).sum();
    }

    public void removeBand(Band band) {
        if (band.equals(this.band)) {
            band.removeAlbum(this);
            this.band = null;
        }
    }

    protected void updateBand(Band band) {
        this.band = band;
    }

    /**
     * Dodaje utwór do albumu.
     */
    public void addSong(Song song) {
        if (song != null) {
            songs.add(song);
            song.setAlbum(this);
        }
    }

    public void removeSong(Song song) {
        if (song != null) {
            songs.remove(song);
            song.setAlbum(null);
        }
    }

    /**
     * Sprawdza czy album przypisano do zespołu.
     */
    public boolean hasAssignedBand() {
        return band != null;
    }

    /**
     * Zwraca niemodyfikowalną listę utworów.
     */
    public List<Song> getSongs() {
        return Collections.unmodifiableList(songs);
    }


    /**
     * Reprezentacja tekstowa albumu.
     */
    @Override
    public String toString() {
        return "Album{" +
                "title='" + title + '\'' +
                ", releaseYear=" + releaseDate +
                ", songs=" + songs +
                ", genre=" + genre.getName() +
                '}';
    }
}
