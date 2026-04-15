package com.springmusicapp.domain.band.model;

import com.springmusicapp.domain.catalog.model.Album;
import com.springmusicapp.domain.contract.model.Contract;
import com.springmusicapp.domain.event.Event;
import com.springmusicapp.domain.event.Performance;
import com.springmusicapp.domain.musician.Musician;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Abstrakcyjna klasa reprezentująca zespół muzyczny.
 * Obsługuje relacje z muzykami, albumami, występami i zarządza promocją między klasami dziedziczącymi.
 * Dziedziczy po ObjectExtent – obsługa ekstensji obiektów.
 */

@Getter
@Setter
@Entity
@Table(name = "bands")
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BandStatus status = BandStatus.UNPOPULAR;

    private double earnedMoney = 0.0;

    @OneToMany(mappedBy = "band", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BandMembership> memberships = new ArrayList<>();

    @OneToMany(mappedBy = "band", fetch = FetchType.LAZY)
    @OrderBy("releaseDate ASC")
    protected List<Album> albums = new ArrayList<>();

    @OneToMany(mappedBy = "band", fetch = FetchType.LAZY)
    protected List<Performance> performances = new ArrayList<>();

    @OneToOne(mappedBy = "band", cascade = CascadeType.ALL, orphanRemoval = true)
    private Contract contract;

    public Band() {
    }

    public Band(String name) {
            setName(name);
    }

    public int getBandViews() {
        return albums.stream().mapToInt(Album::getTotalViews).sum();
    }


    public void evaluatePromotion() {
        if (status == BandStatus.UNPOPULAR && getBandViews() >= 10000) {
            this.status = BandStatus.POPULAR;
        } else if (status == BandStatus.POPULAR && getBandViews() < 10000) {
            this.status = BandStatus.UNPOPULAR;
            if (this.contract != null) {
                this.removeContract();
            }
        }
    }

    public void addContract(Contract newContract) {
        if (this.status == BandStatus.UNPOPULAR) {
            throw new IllegalStateException("Unpopular band cannot sign contracts with managers");
        }

        if (newContract == null) throw new IllegalArgumentException("Contract cannot be null");

        this.contract = newContract;
        newContract.setBand(this);
    }

    public void removeContract() {
        if (this.contract != null) {
            this.contract.setBand(null);
            this.contract = null;
        }
    }

    public void addAlbum(Album album) {
        if (!albums.contains(album) && !album.hasAssignedBand()) {
            albums.add(album);
            album.setBand(this);
            evaluatePromotion();
        }
    }


    /**
     * Ustawia nazwę zespołu.
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Band's name cannot be empty");
        }
        this.name = name;
    }

    /**
     * Dodaje muzyka do zespołu i przypisuje mu zespół.
     */
    public void addMusician(Musician musician, BandRole role) {
        BandMembership membership = new BandMembership(this, musician, role);
        this.memberships.add(membership);
    }

    /**
     * Usuwa muzyka z zespołu.
     */
    public void removeMembership(BandMembership membership) {
        if (!this.memberships.contains(membership)) {
            throw new IllegalArgumentException("Band membership does not exist");
        }
        this.memberships.remove(membership);
        membership.getMusician().getMemberships().remove(membership);
        
        membership.setBand(null);
        membership.setMusician(null);
    }

    /**
     * Usuwa album z zespołu.
     */
    public void removeAlbum(Album album) {
        if (albums.remove(album)) {
            album.removeBand(this); // usunięcie relacji zwrotnej
        }
    }

    /**
     * Dodaje zespół jako wykonawcę na wydarzeniu.
     */
    public void addPerformanceToEvent(Event event, boolean isMainBand) {
        if (performances.stream().noneMatch(performance -> performance.getEvent().equals(event))) {
            new Performance(event, this, isMainBand);
        }
    }

    /**
     * Usuwa występ z listy i usuwa relację dwustronną.
     */
    public void removePerformance(Performance performance) {
        if (performances.contains(performance)) {
            performances.remove(performance);
            performance.removePerformance();
        }
    }

    /**
     * Dodaje występ – zabezpieczenie przed duplikatami.
     */
    public void addPerformance(Performance performance) {
        if (performances.contains(performance)) {
            throw new IllegalArgumentException("Performance already exists");
        }
        performances.add(performance);
    }


    public List<Performance> getPerformances() {
        return Collections.unmodifiableList(performances);
    }

    public List<Musician> getMembers() {
        return memberships.stream().map(BandMembership::getMusician).toList();
    }

    public List<Album> getAlbums() {
        return Collections.unmodifiableList(albums);
    }

    @Override
    public String toString() {
        return name;
    }
}
