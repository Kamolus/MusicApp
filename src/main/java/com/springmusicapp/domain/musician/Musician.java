package com.springmusicapp.domain.musician;

import com.springmusicapp.domain.user.model.User;
import com.springmusicapp.domain.band.Band;
import com.springmusicapp.domain.catalog.model.Song;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "musicians")
public class Musician extends User {

    @ElementCollection(targetClass = MusicianType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "musician_types", joinColumns = @JoinColumn(name = "musician_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Set<MusicianType> types;
    @NotBlank
    @Column(nullable = false)
    private String stageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_id")
    private Band currentBand;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Song> guestAppearances;

    public Musician() {
    }

    public Musician(String id, String name, String email, String stageName, EnumSet<MusicianType> types) {
        super(id, name, email);
        setStageName(stageName);
        this.types = types;
    }

    public void setStageName(String stageName) {
        if (stageName == null || stageName.isBlank()) {
            throw new IllegalArgumentException("Stage name cannot be null or empty");
        }
        this.stageName = stageName;
    }

    public boolean isAvailable() {
        return currentBand == null;
    }

    public boolean hasType(MusicianType type) {
        return types.contains(type);
    }

    public void assignToBand(Band band) {
        if (band == null) {
            throw new IllegalArgumentException("Band cannot be null");
        }
        if (!isAvailable()) {
            throw new IllegalStateException("Musician is already in a band");
        }
        this.currentBand = band;
        band.addMusician(this);
    }

    public void removeBand(){
        if (currentBand != null) {
            currentBand.removeMusician(this);
            this.currentBand = null;
      }
    }

    protected void updateBand(Band band) {
        this.currentBand = band;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Musician musician = (Musician) o;
        return Objects.equals(types, musician.types) && Objects.equals(stageName, musician.stageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), types, stageName);
    }

    @Override
    public String toString() {
        return "Musician{" +
                "stageName='" + getStageName() + '\'' +
                ", types=" + getTypes() + '\'' +
                '}';
    }
}