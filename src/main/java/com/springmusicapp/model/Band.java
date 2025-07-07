package com.springmusicapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Abstrakcyjna klasa reprezentująca zespół muzyczny.
 * Obsługuje relacje z muzykami, albumami, występami i zarządza promocją między klasami dziedziczącymi.
 * Dziedziczy po ObjectExtent – obsługa ekstensji obiektów.
 */


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    private int totalSells; // obliczane dynamicznie z albumów

    @OneToMany
    protected final List<Musician> members = new ArrayList<>();

    @OneToMany
    protected List<Album> albums = new ArrayList<>();

    @OneToMany
    protected List<Performance> performances = new ArrayList<>();

    public Band() {
    }

    /**
     * Konstruktor zespołu – wymaga tylko nazwy.
     */

    public Band(String name) {
            setName(name);
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
    public void addMusician(Musician musician) {
        if (!members.contains(musician)) {
            members.add(musician);
            musician.assignToBand(this); // dwustronna relacja
        }
    }

    /**
     * Dodaje album do zespołu i ustawia sortowanie po roku wydania.
     * Sprawdza też możliwość awansu zespołu.
     */
    public void addAlbum(Album album) {
        if (!albums.contains(album) && !album.hasAssignedBand()) {
            albums.add(album);
            album.setBand(this);
            albums.sort(Comparator.comparing(Album::getReleaseYear));

            if (canBePopular()) {
                System.out.println("U can be popularBand!");
            }
        }
    }

    /**
     * Usuwa muzyka z zespołu.
     */
    public void removeMusician(Musician musician) {
        if (members.contains(musician)) {
            members.remove(musician);
            musician.removeBand(); // usunięcie relacji zwrotnej
        }
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

    /**
     * Ewaluacja promocji/degradacji zespołu na podstawie sprzedaży.
     * Tworzy nową instancję klasy (PopularBand / UnpopularBand), usuwa starą.
     */
    public Band evaluatePromotionAndReturn() {
        if (this instanceof PopularBand popular && getTotalSells() < 10000) {
            System.out.println("Zespół zdegradowany do UnpopularBand");
            //dodac usuwanie z bazy
            return new UnpopularBand(popular);
        } else if (this instanceof UnpopularBand unpopular && getTotalSells() >= 10000) {
            System.out.println("Zespół awansowany do PopularBand");
            //dodac usuwanie z bazy
            return new PopularBand(unpopular);
        }
        // Brak zmiany – zwracamy ten sam obiekt
        //dodac usuwanie z bazy
        return this;
    }

    /**
     * Sprawdza czy zespół może awansować do klasy PopularBand.
     */
    private boolean canBePopular() {
        return this instanceof UnpopularBand && getTotalSells() > 10000;
    }

    /**
     * Oblicza łączną sprzedaż albumów.
     */
    public int getTotalSells() {
        return albums.stream().mapToInt(Album::getSells).sum();
    }

    // GETTERY

    public List<Performance> getPerformances() {
        return Collections.unmodifiableList(performances);
    }

    public List<Musician> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public List<Album> getAlbums() {
        return Collections.unmodifiableList(albums);
    }

    public String getName() {
        return name;
    }

    /**
     * Reprezentacja tekstowa – tylko nazwa (dla JComboBox itp.).
     */
    @Override
    public String toString() {
        return name;
    }
}
