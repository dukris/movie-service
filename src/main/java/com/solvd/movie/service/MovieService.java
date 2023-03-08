package com.solvd.movie.service;

import com.solvd.movie.domain.Movie;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieService {

    Flux<Movie> retrieveAll();

    Mono<Movie> retrieveById(Long movieId);

    Mono<Boolean> isExist(Long movieId);

    Mono<Movie> create(Movie movie);

}
