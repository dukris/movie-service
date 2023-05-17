package com.solvd.movie.service;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.criteria.SearchCriteria;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * MovieService for Elasticsearch
 */

public interface EsMovieService {

    Flux<EsMovie> retrieveAllByCriteria(
            SearchCriteria searchCriteria,
            Pageable pageable);

    Mono<EsMovie> create(EsMovie movie);

    Mono<EsMovie> update(EsMovie movie);

    Mono<Void> delete(Long movieId);

}
