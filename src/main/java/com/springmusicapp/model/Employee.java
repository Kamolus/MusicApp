package com.springmusicapp.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Abstrakcyjna klasa reprezentująca pracownika systemu.
 * Dziedziczy po klasie {@link User}, dodając atrybuty związane z zatrudnieniem:
 * datę zatrudnienia oraz wysokość wynagrodzenia.
 * <p>
 * Przykładowe klasy potomne: {@link MusicianScout}, {@link EventManager}, {@link BandManager}
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "employees")
public abstract class Employee extends User {
    /** Data zatrudnienia pracownika. */
    @Column(nullable = false)
    private LocalDate hireDate;

    /** Wynagrodzenie pracownika. */
    @Column(nullable = false)
    private double salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "label_id")
    private MusicLabel musicLabel;

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
     * @param salary wartość nieujemna i nie mniejsza od poprzedniej
     */
    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        if (salary < this.salary) {
            throw new IllegalArgumentException("Salary can only increase");
        }
        this.salary = salary;
    }

    public void setMusicLabel(MusicLabel musicLabel) {
        if (this.musicLabel != null) {
            this.musicLabel.getEmployees().remove(this);
        }

        this.musicLabel = musicLabel;

        if (musicLabel != null && !musicLabel.getEmployees().contains(this)) {
            musicLabel.getEmployees().add(this);
        }
    }

    public void removeMusicLabel() {
        this.musicLabel = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return Double.compare(salary, employee.salary) == 0 && Objects.equals(hireDate, employee.hireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hireDate, salary);
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
