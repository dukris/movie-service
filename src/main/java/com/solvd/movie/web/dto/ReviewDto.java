package com.solvd.movie.web.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class ReviewDto {

    private final Long id;
    private final String name;
    private final String text;
    private final LocalDate date;
    private final Long movieId;

}
