package com.springmusicapp.domain.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Table(name = "admins")
public class Admin extends User {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminLevel adminLevel;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "requires_password_change")
    private boolean requiresPasswordChange;

    @Column(name = "last_password_change")
    private LocalDate lastPasswordChangeDate;


    public Admin(String id, String name, String email, AdminLevel adminLevel, String phoneNumber) {
        super(id, name, email);
        this.adminLevel = adminLevel;
        this.phoneNumber = phoneNumber;
        this.requiresPasswordChange = true;
    }

    public boolean isRequiresPasswordChange() {
        if (this.requiresPasswordChange) return true;
        if (this.lastPasswordChangeDate == null) return true;

        return LocalDate.now().isAfter(lastPasswordChangeDate.plusDays(90));
    }
}
