package com.springmusicapp.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopularBandDTO {

    @NotBlank
    private Long id;

    @NotBlank
    private String name;

    private int totalSells;

    private double earnedMoney;

    private Long contractId;
}
