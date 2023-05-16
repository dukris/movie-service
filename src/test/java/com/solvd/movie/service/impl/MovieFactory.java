package com.solvd.movie.service.impl;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.Movie;
import com.solvd.movie.model.Quality;

public final class MovieFactory {

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

}
