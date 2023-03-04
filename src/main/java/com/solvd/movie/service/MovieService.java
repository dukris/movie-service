package com.solvd.movie.service;

import com.solvd.movie.domain.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> retrieveAll();

    Boolean isExists(Long movieId);

    Movie create(Movie movie);

}
