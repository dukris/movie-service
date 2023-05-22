package com.solvd.movie.persistence;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.service.impl.ModelFactory;
import integration.ITCase;
import integration.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
@ActiveProfiles("test")
public class EsMovieRepositoryITest extends ITCase {

    @Autowired
    private EsMovieRepository movieRepository;

    @Test
    public void verifyFindAll() {
        SearchCriteria criteria = ModelFactory.getEmptyCriteria();
        EsMovie movie = ModelFactory.getEsMovie();
        Mono<EsMovie> createdMovie = this.movieRepository.save(movie);
        StepVerifier.create(createdMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
        Flux<EsMovie> movies = this.movieRepository.findAllByCriteria(
                criteria, PageRequest.of(0, 20)
        );
        StepVerifier.create(movies)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifyFindByCriteria() {
        SearchCriteria criteria = ModelFactory.getCriteria();
        EsMovie movie = ModelFactory.getEsMovie();
        Mono<EsMovie> createdMovie = this.movieRepository.save(movie);
        StepVerifier.create(createdMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
        Flux<EsMovie> movies = this.movieRepository.findAllByCriteria(
                criteria, PageRequest.of(0, 20)
        );
        StepVerifier.create(movies)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifySave() {
        EsMovie movie = ModelFactory.getEsMovie();
        Mono<EsMovie> createdMovie = this.movieRepository.save(movie);
        StepVerifier.create(createdMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifyDelete() {
        Long movieId = 1L;
        Mono<Void> result = this.movieRepository.deleteById(movieId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

}
