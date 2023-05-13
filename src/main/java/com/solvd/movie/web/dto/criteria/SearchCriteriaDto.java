package com.solvd.movie.web.dto.criteria;

public record SearchCriteriaDto(

        String name,
        Integer year,
        String country,
        String genre,
        String language,
        String quality

) {
}
