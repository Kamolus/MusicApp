package com.springmusicapp.domain.label.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "music_labels")
public class MusicLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "musicLabel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

    @Column(nullable = false)
    String taxNumber;

    public MusicLabel() {
    }

    public MusicLabel(String name) {
        this.name = name;
    }


    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Label name cannot be blank");
        }
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public void addEmployee(Employee employee) {
        if (employee == null) throw new IllegalArgumentException("Cannot add null employee");
        if (!employees.contains(employee)) {
            employees.add(employee);
            employee.setMusicLabel(this);
        }
    }

    public void removeEmployee(Employee employee) {
        if (employees.remove(employee)) {
            employee.setMusicLabel(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MusicLabel that)) return false;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "MusicLabel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employees=" + employees.size() +
                '}';
    }
}