package com.solvd.movie.service.impl;

import com.solvd.movie.domain.Movie;
import com.solvd.movie.domain.exception.ResourceNotFoundException;
import com.solvd.movie.persistence.MovieRepository;
import com.solvd.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Flux<Movie> retrieveAll() {
        return movieRepository.findAll();
    }

    @Override
    public Mono<Movie> retrieveById(Long movieId) {
        return movieRepository.findById(movieId)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .flatMap(optionalUser -> {
                    if (optionalUser.isEmpty()) {
                        return Mono.error(new ResourceNotFoundException("Movie with id = " + movieId + " doesn't exist!"));
                    }
                    return movieRepository.findById(movieId);
                });
    }

    @Override
    public Mono<Boolean> isExist(Long movieId) {
        return movieRepository.existsById(movieId);
    }

    @Override
    public Mono<Movie> create(Movie movie) {
        return movieRepository.save(movie);
    }

}
