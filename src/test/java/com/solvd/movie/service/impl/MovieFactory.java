package com.solvd.movie.service.impl;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.Movie;

public final class MovieFactory {

    public static EsMovie getEsMovie(){
        EsMovie movie = new EsMovie();
        movie.setId(1L);
        movie.setName("Name");
        movie.setYear(2023);
        return movie;
    }

    public static Movie getMovie(){
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("Name");
        movie.setDescription("Description");
        movie.setYear(2023);
        return movie;
    }

}
