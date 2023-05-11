package com.solvd.movie.persistence;


import com.solvd.movie.model.PgMovie;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface PgMovieRepository extends R2dbcRepository<PgMovie, Long> {

    Mono<Boolean> existsById(Long id);

}
