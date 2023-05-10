package com.solvd.movie.service.impl;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.persistence.EsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class EsMovieServiceTest {

    @Mock
    private EsRepository esRepository;

    @InjectMocks
    private EsMovieServiceImpl esMovieService;

    @Test
    public void retrieveAll() {
        EsMovie movie = new EsMovie();
        movie.setId(1L);
        movie.setName("Name");
        movie.setDescription("Description");
        movie.setYear(2023);
        Mockito.when(this.esRepository.findAll())
                .thenReturn(Flux.just(movie));
        Flux<EsMovie> movies = this.esMovieService.retrieveAllByCriteria(
                new SearchCriteria()
        );
        StepVerifier.create(movies)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void retrieveAllByYear() {
        EsMovie movie = new EsMovie();
        movie.setId(1L);
        movie.setName("Name");
        movie.setDescription("Description");
        movie.setYear(2023);
        SearchCriteria criteria = new SearchCriteria();
        criteria.setYear(2023);
        Mockito.when(this.esRepository.findAllByYear(movie.getYear()))
                .thenReturn(Flux.just(movie));
        Flux<EsMovie> movies = this.esMovieService.retrieveAllByCriteria(
                criteria
        );
        StepVerifier.create(movies)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void retrieveAllByName() {
        EsMovie movie = new EsMovie();
        movie.setId(1L);
        movie.setName("Name");
        movie.setDescription("Description");
        movie.setYear(2023);
        SearchCriteria criteria = new SearchCriteria();
        criteria.setName("Name");
        Mockito.when(this.esRepository.findAllByName(movie.getName()))
                .thenReturn(Flux.just(movie));
        Flux<EsMovie> movies = this.esMovieService.retrieveAllByCriteria(
                criteria
        );
        StepVerifier.create(movies)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void retrieveAllByNameAndeYear() {
        EsMovie movie = new EsMovie();
        movie.setId(1L);
        movie.setName("Name");
        movie.setDescription("Description");
        movie.setYear(2023);
        SearchCriteria criteria = new SearchCriteria();
        criteria.setName("Name");
        criteria.setYear(2023);
        Mockito.when(this.esRepository.findAllByNameAndYear(
                        movie.getName(),
                        movie.getYear()
                ))
                .thenReturn(Flux.just(movie));
        Flux<EsMovie> movies = this.esMovieService.retrieveAllByCriteria(
                criteria
        );
        StepVerifier.create(movies)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void create() {
        EsMovie movie = new EsMovie();
        movie.setId(1L);
        movie.setName("Name");
        movie.setDescription("Description");
        movie.setYear(2023);
        Mockito.when(this.esRepository.save(movie))
                .thenReturn(Mono.just(movie));
        Mono<EsMovie> createdMovie = this.esMovieService.create(movie);
        StepVerifier.create(createdMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void update(){
        EsMovie movie = new EsMovie();
        movie.setId(1L);
        movie.setName("Name");
        movie.setDescription("Description");
        movie.setYear(2023);
        Mockito.when(this.esRepository.save(movie))
                .thenReturn(Mono.just(movie));
        Mockito.when(this.esRepository.findById(movie.getId()))
                .thenReturn(Mono.just(movie));
        Mono<EsMovie> updatedMovie = this.esMovieService.update(movie);
        StepVerifier.create(updatedMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void delete(){
        Long movieId = 1L;
        Mockito.when(this.esRepository.deleteById(movieId))
                .thenReturn(Mono.empty());
        Mono<Void> result = this.esMovieService.delete(movieId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

}
