package com.springmusicapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBandDTO {

    @NotBlank(message = "Band's name cannot be empty")
    private String name;
}
