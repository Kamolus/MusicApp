package com.springmusicapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AlbumDTO {

    @NotBlank
    private String title;

    private String releaseDate;

    private String genre;

    @NotBlank
    private String bandName;

    @NotBlank
    private List<String> songTitles;
}
