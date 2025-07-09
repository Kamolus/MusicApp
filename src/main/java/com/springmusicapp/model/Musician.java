package com.springmusicapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.EnumSet;
import java.util.Objects;

@Entity
@Table(name = "musicians")
public class Musician extends User  {

    @ElementCollection(targetClass = MusicianType.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "musician_types", joinColumns = @JoinColumn(name = "musician_id"))
    @Column(name = "type")
    private EnumSet<MusicianType> types;

    @NotBlank
    @Column(nullable = false)
    private String stageName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "band_id")
    private Band currentBand;

    public Musician() {
    }

    public Musician(String name, String email, String stageName, EnumSet<MusicianType> types) {
        super(name, email);
        setStageName(stageName);
        this.types = types;
    }

    public String getStageName() {
        return stageName;
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
        if (isAvailable() && band != null) {
            this.currentBand = band;
            band.addMusician(this);
        }
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

    public EnumSet<MusicianType> getTypes() {
        return types;
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