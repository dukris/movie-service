package com.solvd.movie.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record MovieDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,

        @NotBlank(message = "Name of movie can't be blank!")
        String name,

        @Positive
        @NotNull(message = "Year can't be empty!")
        Integer year,

        @NotBlank(message = "Country can't be blank!")
        String country,

        @NotBlank(message = "Genre can't be blank!")
        String genre,

        @NotBlank(message = "Language can't be blank!")
        String language,

        @NotBlank(message = "Quality can't be blank!")
        Integer quality,

        @NotBlank(message = "Description can't be blank!")
        String description

) {
}
