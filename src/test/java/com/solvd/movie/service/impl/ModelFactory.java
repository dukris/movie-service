package com.solvd.movie.service.impl;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.Movie;
import com.solvd.movie.model.Quality;
import com.solvd.movie.model.criteria.SearchCriteria;

import java.util.List;

public final class ModelFactory {

    public static EsMovie getEsMovie() {
        EsMovie movie = new EsMovie();
        movie.setId(1L);
        movie.setName("Film");
        movie.setYear(2019);
        movie.setLanguage("language");
        movie.setCountry("USA");
        movie.setGenre("genre");
        Quality quality = new Quality();
        quality.setFrom(360);
        quality.setTo(1024);
        movie.setQuality(quality);
        return movie;
    }

    public static Movie getMovie() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("Film");
        movie.setYear(2019);
        movie.setLanguage("language");
        movie.setCountry("USA");
        movie.setGenre("genre");
        movie.setQualityFrom(360);
        movie.setQualityTo(1024);
        movie.setDescription("Description");
        return movie;
    }

    public static SearchCriteria getCriteria(){
        SearchCriteria criteria = new SearchCriteria();
        criteria.setYearFrom(2000);
        criteria.setYearTo(2020);
        criteria.setName("Film");
        criteria.setGenres(List.of("genre", "genre2"));
        criteria.setCountry("USA");
        criteria.setLanguage("language");
        criteria.setQuality(720);
        return criteria;
    }

    public static SearchCriteria getEmptyCriteria(){
        return new SearchCriteria();
    }

}
