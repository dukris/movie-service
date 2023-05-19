package com.solvd.movie.persistence;


import com.solvd.movie.model.Movie;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

@Profile("active")
public interface PgMovieRepository extends R2dbcRepository<Movie, Long> {

    Mono<Boolean> existsById(Long id);

}
