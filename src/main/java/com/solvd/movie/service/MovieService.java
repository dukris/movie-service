package com.solvd.movie.service;

import com.solvd.movie.model.Movie;
import com.solvd.movie.model.criteria.SearchCriteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Main MovieService
 */

public interface MovieService {

    Flux<Movie> retrieveAllByCriteria(SearchCriteria searchCriteria);

    Mono<Movie> retrieveById(Long movieId);

    Mono<Boolean> isExist(Long movieId);

    Mono<Movie> create(Movie movie);

    Mono<Movie> update(Movie movie);

    Mono<Void> delete(Long movieId);

}
