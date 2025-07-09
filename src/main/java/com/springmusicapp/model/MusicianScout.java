package com.springmusicapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
@Table(name = "muscian_scouts")
public class MusicianScout extends Employee implements IScout {

    @NotBlank
    private String phoneNumber;

    public MusicianScout(String name, String email, LocalDate hireDate, double salary, String phoneNumber) {
        super(name, email, hireDate, salary);
        setPhoneNumber(phoneNumber);
    }

    public MusicianScout() {}

    @Override
    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber != null && !phoneNumber.isEmpty()){
            this.phoneNumber = phoneNumber;
        }
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }
}