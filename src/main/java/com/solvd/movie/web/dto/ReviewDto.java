package com.solvd.movie.web.dto;

import java.time.LocalDate;

public record ReviewDto(

        Long id,
        String name,
        String text,
        LocalDate date,
        Long movieId

) {
}
