package com.solvd.movie.persistence;


import com.solvd.movie.domain.Movie;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MovieRepository extends ReactiveCrudRepository<Movie, Long> {

    Mono<Boolean> existsById(Long id);

}
