package com.solvd.movie.persistence;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.model.fake.FkEsMovie;
import com.solvd.movie.model.fake.FkSearchCriteria;
import integration.EsConfig;
import integration.ITCase;
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
@ContextConfiguration(classes = {EsConfig.class})
@ActiveProfiles("test")
@SuppressWarnings("JTCOP.RuleAllTestsHaveProductionClass")
public class EsMovieRepositoryIT extends ITCase {

    @Autowired
    private EsMovieRepository movieRepository;

    @Test
    public void verifiesFindAll() {
        SearchCriteria criteria = new FkSearchCriteria();
        EsMovie movie = new FkEsMovie();
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
    public void verifiesFindByCriteria() {
        SearchCriteria criteria = new FkSearchCriteria();
        EsMovie movie = new FkEsMovie();
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
    public void verifiesSave() {
        EsMovie movie = new FkEsMovie();
        Mono<EsMovie> createdMovie = this.movieRepository.save(movie);
        StepVerifier.create(createdMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifiesDelete() {
        Long movieId = 1L;
        Mono<Void> result = this.movieRepository.deleteById(movieId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

}
