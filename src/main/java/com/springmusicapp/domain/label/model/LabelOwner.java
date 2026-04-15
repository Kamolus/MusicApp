package com.springmusicapp.domain.label.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "label_owners")
public class LabelOwner extends Employee {

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "position")
    private String position;

    public LabelOwner(String id, String name, String email, LocalDate hireDate, double salary, String phoneNumber, String position) {
        super(id, name, email, hireDate, salary);
        this.phoneNumber = phoneNumber;
        this.position = position;
    }

    public LabelOwner() {}
}
