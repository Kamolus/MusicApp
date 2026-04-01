package com.springmusicapp.domain.catalog.model;

import com.springmusicapp.domain.musician.Musician;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "songs")
public class Song{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String spotifyId;

    @Column
    private Integer duration;

    @Column(nullable = false)
    private String title;

    @Column
    private int views = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToMany
    @JoinTable(
            name = "collaborators",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "musician_id")
    )
    private Set<Musician> studioMusicians = new HashSet<>();

    public Song() {
    }

    public Song(int duration, String title) {
        setDuration(duration);
        setTitle(title);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration <= 0){
            throw new IllegalArgumentException("Duration must be greater than 0");
        }
        this.duration = duration;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()){
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }

    public void setViews(int views) {
        if (views < 0){
            throw new IllegalArgumentException("Views must be greater than 0");
        }
        this.views = views;
    }

    public void removeAlbum() {
        if (this.album != null) {
            this.album.removeSong(this);
        }
    }

    public List<Musician> getMusicians() {
        return List.copyOf(studioMusicians);
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", duration=" + getDuration() +
                '}';
    }
}
