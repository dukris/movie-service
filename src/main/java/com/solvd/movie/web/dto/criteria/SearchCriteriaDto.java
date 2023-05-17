package com.solvd.movie.web.dto.criteria;

import java.util.List;

public record SearchCriteriaDto(

        String name,
        Integer yearFrom,
        Integer yearTo,
        String country,
        List<String> genres,
        String language,
        Integer quality

) {
}