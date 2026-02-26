package com.springmusicapp.dto;

import com.springmusicapp.model.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BandDTO {

    @NotBlank
    private String name;

    private BandStatus bandStatus;

    private List<String> musiciansName;

    private List<String> albumsTitles;

    private boolean hasActiveContract;
}
