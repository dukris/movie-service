package com.solvd.movie.persistence;


import com.solvd.movie.domain.Movie;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface MovieRepository extends R2dbcRepository<Movie, Long> {

    Mono<Boolean> existsById(Long id);

}
