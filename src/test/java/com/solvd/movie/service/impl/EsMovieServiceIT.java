package com.solvd.movie.service.impl;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.service.EsMovieService;
import integration.EsContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@SpringBootTest
@Testcontainers
public class EsMovieServiceIT {

    @Container
    private static final ElasticsearchContainer container = new EsContainer();

    @DynamicPropertySource
    private static void properties(final DynamicPropertyRegistry registry) {
        registry.add("spring.elasticsearch.uris", container::getHttpHostAddress);
        registry.add("kafka.bootstrap-servers", () -> "localhost");
    }

    @Autowired
    private EsMovieService esMovieService;

    @BeforeAll
    public static void start() {
        container.start();
    }

    @Test
    public void verifyFindAll() {
        EsMovie movie = MovieFactory.getEsMovie();
        Pageable pageable = PageRequest.of(0, 20);
        Mono<EsMovie> createdMovie = this.esMovieService.create(movie);
        StepVerifier.create(createdMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
        Flux<EsMovie> movies = this.esMovieService.retrieveAllByCriteria(
                new SearchCriteria(), pageable
        );
        StepVerifier.create(movies)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifyFindByCriteria() {
        SearchCriteria criteria = new SearchCriteria();
        criteria.setYearFrom(2000);
        criteria.setYearTo(2020);
        criteria.setName("Film");
        criteria.setGenres(List.of("genre", "genre2"));
        criteria.setCountry("USA");
        criteria.setLanguage("language");
        criteria.setQuality(720);
        EsMovie movie = MovieFactory.getEsMovie();
        Pageable pageable = PageRequest.of(0, 20);
        Mono<EsMovie> createdMovie = this.esMovieService.create(movie);
        StepVerifier.create(createdMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
        Flux<EsMovie> movies = this.esMovieService.retrieveAllByCriteria(
                criteria, pageable
        );
        StepVerifier.create(movies)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifyCreate() {
        EsMovie movie = MovieFactory.getEsMovie();
        Mono<EsMovie> createdMovie = this.esMovieService.create(movie);
        StepVerifier.create(createdMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifyUpdate() {
        EsMovie movie = MovieFactory.getEsMovie();
        Mono<EsMovie> createdMovie = this.esMovieService.update(movie);
        StepVerifier.create(createdMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifyDelete() {
        Long movieId = 1L;
        Mono<Void> result = this.esMovieService.delete(movieId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

    @AfterAll
    public static void stop() {
        container.stop();
    }

}
