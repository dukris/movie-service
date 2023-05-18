package com.solvd.movie.persistence;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.service.impl.ModelFactory;
import integration.EsContainer;
import integration.TestProperties;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest()
@Testcontainers
public class EsMovieRepositoryIT {

    @Container
    private static final ElasticsearchContainer container = new EsContainer().init();

    @Autowired
    private EsMovieRepository movieRepository;

    @BeforeAll
    public static void start() {
        container.start();
    }

    @DynamicPropertySource
    private static void properties(final DynamicPropertyRegistry registry) {
        new TestProperties().set(registry);
    }

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

    @AfterAll
    public static void stop() {
        container.stop();
    }

}
