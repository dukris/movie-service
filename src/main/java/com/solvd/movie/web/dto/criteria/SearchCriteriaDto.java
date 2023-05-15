package com.solvd.movie.web.dto.criteria;

public record SearchCriteriaDto(

        String name,
        Integer yearFrom,
        Integer yearTo,
        String country,
        String genre,
        String language,
        Integer quality

) {
}