package com.springmusicapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private BandManager manager;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "band_id")
    private Band band;

    private int duration;

    private double basePayment;

    private double payPerPerformance;

    /**
     * Konstruktor tworzący nowy kontrakt.
     *
     * manager            Menedżer podpisujący kontrakt.
     * band               Zespół, z którym podpisywany jest kontrakt (musi być popularny).
     * duration           Czas trwania kontraktu (w miesiącach lub latach).
     * basePayment        Podstawowa wypłata.
     * payPerPerformance  Wypłata za występ.
     */

    public Contract() {

    }

    public Contract(BandManager manager, Band band, int duration,
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
