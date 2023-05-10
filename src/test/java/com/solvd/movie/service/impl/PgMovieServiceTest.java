package com.solvd.movie.service.impl;

import com.solvd.movie.kafka.producer.KafkaProducer;
import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.PgMovie;
import com.solvd.movie.model.exception.ResourceNotFoundException;
import com.solvd.movie.persistence.PgRepository;
import com.solvd.movie.web.dto.mapper.MovieMapper;
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

    private final FakeMovieService fakeMovieService = new FakeMovieService();

    @Mock
    private PgRepository pgRepository;

    @Mock
    private KafkaProducer kafkaProducer;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private PgMovieServiceImpl pgMovieService;

    @Test
    public void retrieveByCorrectId() {
        PgMovie movie = this.fakeMovieService.getPgMovie();
        Mockito.when(this.pgRepository.findById(movie.getId()))
                .thenReturn(Mono.just(movie));
        Mono<PgMovie> foundMovie = this.pgMovieService.retrieveById(
                movie.getId()
        );
        StepVerifier.create(foundMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void retrieveByIncorrectId() {
        Long movieId = 1L;
        Mockito.when(this.pgRepository.findById(movieId))
                .thenReturn(Mono.justOrEmpty(Optional.empty()));
        Mono<PgMovie> foundMovie = this.pgMovieService.retrieveById(movieId);
        StepVerifier.create(foundMovie)
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    public void isExist() {
        Long movieId = 1L;
        Mockito.when(this.pgRepository.existsById(movieId))
                .thenReturn(Mono.just(true));
        Mono<Boolean> result = this.pgMovieService.isExist(movieId);
        StepVerifier.create(result)
                .expectNext(true)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void create() {
        PgMovie movie = this.fakeMovieService.getPgMovie();
        Mockito.when(this.pgRepository.save(movie))
                .thenReturn(Mono.just(movie));
        Mockito.when(this.movieMapper.toEntity(movie))
                .thenReturn(new EsMovie());
        Mono<PgMovie> createdMovie = this.pgMovieService.create(movie);
        StepVerifier.create(createdMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void update() {
        PgMovie movie = this.fakeMovieService.getPgMovie();
        Mockito.when(this.pgRepository.findById(movie.getId()))
                .thenReturn(Mono.just(movie));
        Mockito.when(this.pgRepository.save(movie))
                .thenReturn(Mono.just(movie));
        Mockito.when(this.movieMapper.toEntity(movie))
                .thenReturn(new EsMovie());
        Mono<PgMovie> updatedMovie = this.pgMovieService.update(movie);
        StepVerifier.create(updatedMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void delete() {
        Long movieId = 1L;
        Mockito.when(this.pgRepository.deleteById(movieId))
                .thenReturn(Mono.empty());
        Mono<Void> result = this.pgMovieService.delete(movieId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

}
