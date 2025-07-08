package com.springmusicapp.model;

import jakarta.persistence.*;

/**
 * Klasa reprezentująca kontrakt pomiędzy menedżerem zespołu a popularnym zespołem muzycznym.
 * Kontrakt zawiera informacje o długości trwania umowy oraz wynagrodzeniu.
 * <p>
 * Obiekt jest automatycznie dodawany do ekstensji oraz do odpowiednich kolekcji
 * w {@link BandManager} i {@link PopularBand}.
 */

@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private BandManager manager;
    @OneToOne(fetch = FetchType.EAGER)
    private PopularBand band;
    private int duration;
    private double basePayment;
    private double payPerPerformance;

    /**
     * Konstruktor tworzący nowy kontrakt.
     *
     * manager            Menedżer podpisujący kontrakt.
     * band               Zespół, z którym podpisywany jest kontrakt (musi być PopularBand).
     * duration           Czas trwania kontraktu (w miesiącach lub latach).
     * basePayment        Podstawowa wypłata.
     * payPerPerformance  Wypłata za występ.
     */

    public Contract() {

    }

    public Contract(BandManager manager, PopularBand band, int duration,
                    double basePayment, double payPerPerformance) {
        this.manager = manager;
        this.band = band;
        setDuration(duration);
        setBasePayment(basePayment);
        setPayPerPerformance(payPerPerformance);

        // Relacje dwustronne
        manager.addContract(this);
        band.addContract(this);
    }

    /** Zwraca menedżera przypisanego do kontraktu. */
    public BandManager getManager() {
        return manager;
    }

    /** Zwraca zespół przypisany do kontraktu. */
    public Band getBand() {
        return band;
    }

    /** Zwraca podstawowe wynagrodzenie zespołu. */
    public double getBasePayment() {
        return basePayment;
    }

    /** Zwraca stawkę za występ. */
    public double getPayPerPerformance() {
        return payPerPerformance;
    }

    /**
     * Ustawia długość trwania kontraktu.
     * @param duration liczba większa od 0
     */
    public void setDuration(int duration) {
        if (duration <= 0) {
            throw new IllegalArgumentException("Invalid duration");
        }
        this.duration = duration;
    }

    /**
     * Ustawia podstawowe wynagrodzenie.
     * @param basePayment wartość większa od 0
     */
    public void setBasePayment(double basePayment) {
        if (basePayment <= 0) {
            throw new IllegalArgumentException("Invalid basePayment");
        }
        this.basePayment = basePayment;
    }

    /**
     * Ustawia wynagrodzenie za występ.
     * @param payPerPerformance wartość większa od 0
     */
    public void setPayPerPerformance(double payPerPerformance) {
        if (payPerPerformance <= 0) {
            throw new IllegalArgumentException("Invalid payPerPerformance");
        }
        this.payPerPerformance = payPerPerformance;
    }

    /**
     * Usuwa kontrakt z systemu i czyści relacje z zespołem i menedżerem.
     */
    public void removeContract() {
        manager.removeContract(this);
        band.removeContract();  // zakładamy, że dany zespół ma tylko jeden kontrakt
        //dodac usuwanie
    }

    @Override
    public String toString() {
        return "Contract{" +
                "manager=" + manager.getName() +
                ", band=" + band.getName() +
                ", duration=" + duration +
                ", basePayment=" + basePayment +
                ", payPerPerformance=" + payPerPerformance +
                '}';
    }
}
