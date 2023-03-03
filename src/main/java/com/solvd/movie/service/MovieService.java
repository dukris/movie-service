package com.solvd.movie.service;

import com.solvd.movie.domain.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> retrieveAll();

    Movie create(Movie movie);

}
