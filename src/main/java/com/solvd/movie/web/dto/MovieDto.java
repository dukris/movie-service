package com.solvd.movie.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
public class MovieDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @NotBlank(message = "Name of movie can't be blank!")
    private final String name;

    @NotBlank(message = "Description can't be blank!")
    private final String description;

    @Positive
    @NotNull(message = "Year can't be empty!")
    private final Integer year;

}
