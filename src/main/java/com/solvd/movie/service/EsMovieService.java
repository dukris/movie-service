package com.solvd.movie.service;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.criteria.SearchCriteria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EsMovieService {

    Flux<EsMovie> retrieveAllByCriteria(SearchCriteria searchCriteria);

    Mono<EsMovie> create(EsMovie movie);

    Mono<EsMovie> update(EsMovie movie);

    Mono<Void> delete(Long movieId);

}
