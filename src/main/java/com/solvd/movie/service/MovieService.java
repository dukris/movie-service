package com.solvd.movie.service;

import com.solvd.movie.domain.PgMovie;
import com.solvd.movie.domain.criteria.SearchCriteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieService {

    Flux<PgMovie> retrieveAllByCriteria(SearchCriteria searchCriteria);

    Mono<PgMovie> retrieveById(Long movieId);

    Mono<Boolean> isExist(Long movieId);

    Mono<PgMovie> create(PgMovie movie);

    void delete(Long movieId);

}
