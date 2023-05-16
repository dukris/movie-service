package com.solvd.movie.service.impl;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.persistence.EsMovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class EsMovieServiceTest {

    @Mock
    private EsMovieRepository esMovieRepository;

    @Mock
    private ReactiveElasticsearchOperations operations;

    @InjectMocks
    private EsMovieServiceImpl esMovieService;

    @Test
    public void verifyRetrieveAll() {
        Pageable pageable = PageRequest.of(0, 20);
        Mockito.when(this.operations.search(
                        Mockito.any(CriteriaQuery.class),
                        Mockito.eq(EsMovie.class),
                        Mockito.any(IndexCoordinates.class))
                ).thenReturn(Flux.empty());
        Flux<EsMovie> movies = this.esMovieService.retrieveAllByCriteria(
                new SearchCriteria(),
                pageable
        );
        StepVerifier.create(movies)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifyRetrieveAllByCriteria() {
        SearchCriteria criteria = new SearchCriteria();
        criteria.setYearFrom(2000);
        criteria.setYearTo(2020);
        criteria.setName("Film");
        criteria.setGenre("genre");
        criteria.setCountry("USA");
        criteria.setLanguage("language");
        criteria.setQuality(720);
        Pageable pageable = PageRequest.of(0, 20);
        Mockito.when(this.operations.search(
                Mockito.any(CriteriaQuery.class),
                Mockito.eq(EsMovie.class),
                Mockito.any(IndexCoordinates.class))
        ).thenReturn(Flux.empty());
        Flux<EsMovie> movies = this.esMovieService.retrieveAllByCriteria(
                criteria,
                pageable
        );
        StepVerifier.create(movies)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifyCreate() {
        EsMovie movie = MovieFactory.getEsMovie();
        Mockito.when(this.esMovieRepository.save(movie))
                .thenReturn(Mono.just(movie));
        Mono<EsMovie> createdMovie = this.esMovieService.create(movie);
        StepVerifier.create(createdMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifyUpdate() {
        EsMovie movie = MovieFactory.getEsMovie();
        Mockito.when(this.esMovieRepository.save(movie))
                .thenReturn(Mono.just(movie));
        Mockito.when(this.esMovieRepository.findById(movie.getId()))
                .thenReturn(Mono.just(movie));
        Mono<EsMovie> updatedMovie = this.esMovieService.update(movie);
        StepVerifier.create(updatedMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifyDelete() {
        Long movieId = 1L;
        Mockito.when(this.esMovieRepository.deleteById(movieId))
                .thenReturn(Mono.empty());
        Mono<Void> result = this.esMovieService.delete(movieId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

}
