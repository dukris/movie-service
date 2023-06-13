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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class EsMovieServiceTest {

    @Mock
    private EsMovieRepository esMovieRepository;

    @InjectMocks
    private EsMovieServiceImpl esMovieService;

    @Test
    public void verifiesRetrieveAllByCriteria() {
        SearchCriteria criteria = ModelFactory.getEmptyCriteria();
        Pageable pageable = PageRequest.of(0, 20);
        EsMovie movie = ModelFactory.getEsMovie();
        Mockito.when(this.esMovieRepository.findAllByCriteria(
                criteria,
                pageable
        )).thenReturn(Flux.just(movie));
        Flux<EsMovie> movies = this.esMovieService.retrieveAllByCriteria(
                criteria,
                pageable
        );
        StepVerifier.create(movies)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifiesCreate() {
        EsMovie movie = ModelFactory.getEsMovie();
        Mockito.when(this.esMovieRepository.save(movie))
                .thenReturn(Mono.just(movie));
        Mono<EsMovie> createdMovie = this.esMovieService.create(movie);
        StepVerifier.create(createdMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifiesUpdate() {
        EsMovie movie = ModelFactory.getEsMovie();
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
    public void verifiesDelete() {
        Long movieId = 1L;
        Mockito.when(this.esMovieRepository.deleteById(movieId))
                .thenReturn(Mono.empty());
        Mono<Void> result = this.esMovieService.delete(movieId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

}
