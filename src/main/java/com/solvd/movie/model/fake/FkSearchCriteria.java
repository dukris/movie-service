package com.solvd.movie.model.fake;

import com.solvd.movie.model.criteria.SearchCriteria;

import java.util.List;

public class FkSearchCriteria extends SearchCriteria {

    public FkSearchCriteria() {
        super.setYearFrom(2000);
        super.setYearTo(2025);
        super.setName("Film");
        super.setGenres(List.of("Genre", "Genre2"));
        super.setCountry("Country");
        super.setLanguage("English");
        super.setQuality(720);
    }

}
