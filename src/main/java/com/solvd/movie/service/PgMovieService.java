package com.solvd.movie.service;

import com.solvd.movie.model.PgMovie;
import reactor.core.publisher.Mono;

public interface PgMovieService {

    Mono<PgMovie> retrieveById(Long movieId);

    Mono<Boolean> isExist(Long movieId);

    Mono<PgMovie> create(PgMovie movie);

    Mono<PgMovie> update(PgMovie movie);

    Mono<Void> delete(Long movieId);

}
