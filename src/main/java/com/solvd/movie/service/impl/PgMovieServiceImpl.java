package com.solvd.movie.service.impl;

import com.solvd.movie.model.Movie;
import com.solvd.movie.model.exception.ResourceNotFoundException;
import com.solvd.movie.persistence.PgMovieRepository;
import com.solvd.movie.service.PgMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PgMovieServiceImpl implements PgMovieService {

    private final PgMovieRepository pgMovieRepository;

    @Override
    public Mono<Movie> retrieveById(final Long movieId) {
        return this.pgMovieRepository.findById(movieId)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .flatMap(optionalMovie -> {
                    if (optionalMovie.isEmpty()) {
                        return Mono.error(
                                new ResourceNotFoundException(
                                        "Movie with id = " + movieId
                                                + " doesn't exist!"
                                )
                        );
                    }
                    return Mono.justOrEmpty(optionalMovie);
                });
    }

    @Override
    public Mono<Boolean> isExist(final Long movieId) {
        return this.pgMovieRepository.existsById(movieId);
    }

    @Override
    @Transactional
    public Mono<Movie> create(final Movie movie) {
        return this.pgMovieRepository.save(movie);
    }

    @Override
    @Transactional
    public Mono<Movie> update(final Movie movie) {
        return this.pgMovieRepository.findById(movie.getId())
                .flatMap(foundMovie -> {
                    foundMovie.setName(movie.getName());
                    foundMovie.setYear(movie.getYear());
                    foundMovie.setCountry(movie.getCountry());
                    foundMovie.setGenre(movie.getGenre());
                    foundMovie.setLanguage(movie.getLanguage());
                    foundMovie.setQualityFrom(movie.getQualityFrom());
                    foundMovie.setQualityTo(movie.getQualityTo());
                    foundMovie.setDescription(movie.getDescription());
                    return this.pgMovieRepository.save(foundMovie);
                });
    }

    @Override
    @Transactional
    public Mono<Void> delete(final Long movieId) {
        return this.pgMovieRepository.deleteById(movieId);
    }

}
