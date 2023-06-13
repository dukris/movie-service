package com.solvd.movie.service.impl;

import com.solvd.movie.model.Movie;
import com.solvd.movie.model.exception.ResourceNotFoundException;
import com.solvd.movie.model.fake.FkMovie;
import com.solvd.movie.persistence.PgMovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PgMovieServiceTest {

    @Mock
    private PgMovieRepository pgMovieRepository;

    @InjectMocks
    private PgMovieServiceImpl pgMovieService;

    @Test
    public void verifiesRetrieveByCorrectId() {
        Movie movie = new FkMovie();
        Mockito.when(this.pgMovieRepository.findById(movie.getId()))
                .thenReturn(Mono.just(movie));
        Mono<Movie> foundMovie = this.pgMovieService.retrieveById(
                movie.getId()
        );
        StepVerifier.create(foundMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifiesRetrieveByIncorrectId() {
        Long movieId = 1L;
        Mockito.when(this.pgMovieRepository.findById(movieId))
                .thenReturn(Mono.justOrEmpty(Optional.empty()));
        Mono<Movie> foundMovie = this.pgMovieService.retrieveById(movieId);
        StepVerifier.create(foundMovie)
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    public void verifiesIsExist() {
        Long movieId = 1L;
        Mockito.when(this.pgMovieRepository.existsById(movieId))
                .thenReturn(Mono.just(true));
        Mono<Boolean> result = this.pgMovieService.isExist(movieId);
        StepVerifier.create(result)
                .expectNext(true)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifiesCreate() {
        Movie movie = new FkMovie();
        Mockito.when(this.pgMovieRepository.save(movie))
                .thenReturn(Mono.just(movie));
        Mono<Movie> createdMovie = this.pgMovieService.create(movie);
        StepVerifier.create(createdMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifiesUpdate() {
        Movie movie = new FkMovie();
        Mockito.when(this.pgMovieRepository.findById(movie.getId()))
                .thenReturn(Mono.just(movie));
        Mockito.when(this.pgMovieRepository.save(movie))
                .thenReturn(Mono.just(movie));
        Mono<Movie> updatedMovie = this.pgMovieService.update(movie);
        StepVerifier.create(updatedMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifiesDelete() {
        Long movieId = 1L;
        Mockito.when(this.pgMovieRepository.deleteById(movieId))
                .thenReturn(Mono.empty());
        Mono<Void> result = this.pgMovieService.delete(movieId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

}
