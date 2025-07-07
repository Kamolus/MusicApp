package com.springmusicapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Song{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private int duration;

    @NotBlank
    private String title;

    @ManyToOne
    private Album album;

    public Song() {
    }

    public Song(int duration, String title, int listenCount) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()){
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        if(this.album != null || album == null){
            throw new IllegalArgumentException("Album cannot be null");
        }
        this.album = album;
        album.addSong(this);
    }

    public void removeAlbum() {
        album.removeSong(this);
    }


    @Override
    public String toString() {
        return "Song{" +
                "title='" + getTitle() + '\'' +
                ", duration=" + getDuration() +
                '}';
    }
}
