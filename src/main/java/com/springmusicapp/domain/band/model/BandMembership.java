package com.springmusicapp.domain.band.model;

import com.springmusicapp.domain.musician.Musician;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "band_memberships")
public class BandMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_id", nullable = false)
    private Band band;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musician_id", nullable = false)
    private Musician musician;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BandRole role;

    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();

    public BandMembership() {}

    public BandMembership(Band band, Musician musician, BandRole role) {
        this.band = band;
        this.musician = musician;
        this.role = role;
    }
}