package com.springmusicapp.domain.catalog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String name;

    @NotBlank
    private String nationality;

    @OneToMany(mappedBy = "genre", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Album> albums = new ArrayList<>();

    public Genre() {
    }

    public Genre(String name, String nationality) {
        setName(name);
        setNationality(nationality);
    }

    public void setName(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setNationality(String nationality) {
        if(nationality == null || nationality.isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.nationality = nationality;
    }

    public void addAlbum(Album album) {
        if (album != null && !albums.contains(album)) {
            albums.add(album);
            album.setGenre(this);
        }
    }

    public void removeAlbum(Album album) {
        if (albums.remove(album)) {
            album.setGenre(null);
        }
    }

    public List<Album> getAlbums() {
        return Collections.unmodifiableList(albums);
    }


    @Override
    public String toString() {
        return "Genre{" +
                ",name=" + name + ' ' +
                ",nationality=" + nationality + ' ' +
                '}';
    }
}
