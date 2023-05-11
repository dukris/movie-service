package com.solvd.movie.service.impl;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.PgMovie;

public final class FakeMovieService {

    public static EsMovie getEsMovie(){
        EsMovie movie = new EsMovie();
        movie.setId(1L);
        movie.setName("Name");
        movie.setDescription("Description");
        movie.setYear(2023);
        return movie;
    }

    public static PgMovie getPgMovie(){
        PgMovie movie = new PgMovie();
        movie.setId(1L);
        movie.setName("Name");
        movie.setDescription("Description");
        movie.setYear(2023);
        return movie;
    }

}
