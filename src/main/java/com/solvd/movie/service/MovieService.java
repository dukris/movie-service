package com.solvd.movie.service;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.Movie;
import com.solvd.movie.model.criteria.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Main MovieService
 */

public interface MovieService {

    Flux<Movie> retrieveAllByCriteria(
            SearchCriteria searchCriteria,
            Pageable pageable);

    Mono<Movie> retrieveById(Long movieId);

    Mono<Boolean> isExist(Long movieId);

    Mono<Movie> create(Movie movie);

    Mono<Movie> update(Movie movie);

    Mono<Void> delete(Long movieId);

}
