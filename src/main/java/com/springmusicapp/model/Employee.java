package com.springmusicapp.model;

import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

/**
 * Abstrakcyjna klasa reprezentująca pracownika systemu.
 * Dziedziczy po klasie {@link User}, dodając atrybuty związane z zatrudnieniem:
 * datę zatrudnienia oraz wysokość wynagrodzenia.
 * <p>
 * Przykładowe klasy potomne: {@link MusicianScout}, {@link EventManager}, {@link BandManager}
 */
@MappedSuperclass
public abstract class Employee extends User {
    /** Data zatrudnienia pracownika. */
    private LocalDate hireDate;

    /** Wynagrodzenie pracownika. */
    private double salary;

    /**
     * Konstruktor tworzący nowego pracownika z podanymi danymi.
     *
     * @param name      Imię i nazwisko pracownika.
     * @param email     Adres e-mail.
     * @param hireDate  Data zatrudnienia (nie może być null).
     * @param salary    Wynagrodzenie (musi być nieujemne).
     */
    public Employee(String name, String email, LocalDate hireDate, double salary) {
        super(name, email);
        setHireDate(hireDate);
        setSalary(salary);
    }

    public Employee(){}

    /**
     * Zwraca datę zatrudnienia pracownika.
     * @return hireDate
     */
    public LocalDate getHireDate() {
        return hireDate;
    }

    /**
     * Ustawia datę zatrudnienia.
     * @param hireDate data różna od null
     */
    public void setHireDate(LocalDate hireDate) {
        if (hireDate == null) {
            throw new IllegalArgumentException("Hire date cannot be null");
        }
        this.hireDate = hireDate;
    }

    /**
     * Zwraca wysokość wynagrodzenia.
     * @return salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Ustawia wysokość wynagrodzenia.
     * @param salary wartość nieujemna
     */
    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.salary = salary;
    }

    /**
     * Reprezentacja tekstowa obiektu Employee.
     * @return String zawierający datę zatrudnienia i wynagrodzenie
     */
    @Override
    public String toString() {
        return "Employee{" +
                "hireDate=" + hireDate +
                ", salary=" + salary +
                '}';
    }
}
