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

        @NotBlank(message = "Description can't be blank!")
        String description,

        @Positive
        @NotNull(message = "Year can't be empty!")
        Integer year

) {
}
