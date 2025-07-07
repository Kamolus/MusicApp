package com.springmusicapp.DTO;

import com.springmusicapp.model.MusicianType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class MusicianDTO {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String stageName;

    private Set<MusicianType> types;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Set<MusicianType> getTypes() {
        return types;
    }

    public void setTypes(Set<MusicianType> types) {
        this.types = types;
    }
}
