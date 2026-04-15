package com.springmusicapp.domain.label.model;

import com.springmusicapp.domain.band.model.Band;
import com.springmusicapp.domain.contract.model.Contract;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca menedżera zespołu muzycznego.
 * Dziedziczy po EventManager oraz implementuje interfejs IScout.
 * Może zawierać maksymalnie dwa aktywne kontrakty z zespołami.
 */

@Entity
@Getter
@Setter
@Table(name = "band_managers")
public class BandManager extends Employee implements IScout {

    private static final int MAX_CONTRACTS = 2; // Maksymalna liczba kontraktów, które może mieć menedżer

    @OneToMany
    private List<Contract> contracts = new ArrayList<>();
    private String phoneNumber;

    /**
     * Konstruktor menedżera zespołu.
     * Jeśli numer telefonu jest nieprawidłowy, obiekt zostaje usunięty z extentu.
     */
    public BandManager(String id, String name, String email, LocalDate hireDate, double salary, String phoneNumber) {
        super(id, name, email, hireDate, salary);
        setPhoneNumber(phoneNumber);

    }


    public BandManager() {}

    /**
     * Ustawia numer telefonu z walidacją.
     */
    @Override
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        this.phoneNumber = phoneNumber;
    }

    /**
     * Zwraca numer telefonu.
     */
    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }



    /**
     * Usuwa kontrakt z listy kontraktów menedżera oraz rozłącza go z obiektem Contract.
     */
    public void removeContract(Contract contract) {
        if (contracts.contains(contract)) {
            contracts.remove(contract);
            contract.removeContract(); // metoda usuwająca referencję do managera i zespołu z kontraktu
        }
    }

    /**
     * Tworzy nowy kontrakt z zespołem, jeżeli taki jeszcze nie istnieje.
     */
    public void signContract(Band band, int duration, double basePayment, double payPerPerformance) {
        if (contracts.stream().noneMatch(contract -> contract.getBand().equals(band))) {
            new Contract(this, band, duration, basePayment, payPerPerformance); // kontrakt dodaje się automatycznie do managera przez konstruktor
        }
    }

    /**
     * Dodaje kontrakt do listy kontraktów menedżera.
     * Jeżeli menedżer przekroczy limit, wyrzuca wyjątek.
     */
    public void addContract(Contract contract) {
        if (contracts.size() >= MAX_CONTRACTS) {
            throw new IllegalArgumentException("Maximum number of contracts reached");
        }
        contracts.add(contract);
    }

    /**
     * Zwraca informację tekstową o menedżerze.
     */
    @Override
    public String toString() {
        return "BandManager{" +
                "contracts=" + contracts +
                '}';
    }
}
