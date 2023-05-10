package com.solvd.movie.service.impl;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.PgMovie;

public class FakeMovieService {

    public EsMovie getEsMovie(){
        EsMovie movie = new EsMovie();
        movie.setId(1L);
        movie.setName("Name");
        movie.setDescription("Description");
        movie.setYear(2023);
        return movie;
    }

    public PgMovie getPgMovie(){
        PgMovie movie = new PgMovie();
        movie.setId(1L);
        movie.setName("Name");
        movie.setDescription("Description");
        movie.setYear(2023);
        return movie;
    }

}
