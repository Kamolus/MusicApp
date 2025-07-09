package com.springmusicapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "music_labels")
public class MusicLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "musicLabel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

    public MusicLabel() {
    }

    public MusicLabel(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Label name cannot be blank");
        }
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
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