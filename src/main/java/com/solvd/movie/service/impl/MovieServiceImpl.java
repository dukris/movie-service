package com.solvd.movie.service.impl;

import com.solvd.movie.kafka.producer.KafkaProducer;
import com.solvd.movie.model.Action;
import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.Event;
import com.solvd.movie.model.Movie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.service.EsMovieService;
import com.solvd.movie.service.MovieService;
import com.solvd.movie.service.PgMovieService;
import com.solvd.movie.web.dto.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final EsMovieService esMovieService;
    private final PgMovieService pgMovieService;
    private final KafkaProducer kafkaProducer;
    private final MovieMapper movieMapper;

    @Override
    public Flux<Movie> retrieveAllByCriteria(
            final SearchCriteria searchCriteria,
            final Pageable pageable) {
        return this.esMovieService.retrieveAllByCriteria(
                        searchCriteria, pageable
                )
                .flatMap(movie ->
                        this.pgMovieService.retrieveById(movie.getId()));
    }

    @Override
    public Mono<Movie> retrieveById(final Long movieId) {
        return this.pgMovieService.retrieveById(movieId);
    }

    @Override
    public Mono<Boolean> isExist(final Long movieId) {
        return this.pgMovieService.isExist(movieId);
    }

    @Override
    public Mono<Movie> create(final Movie movie) {
        return this.pgMovieService.create(movie)
                .flatMap(pgMovie -> {
                            Event event = new Event();
                            event.setAction(Action.CREATE_MOVIE);
                            event.setMovie(this.movieMapper.toEntity(pgMovie));
                            this.kafkaProducer.send(event);
                            return Mono.just(pgMovie);
                        }
                );
    }

    @Override
    public Mono<Movie> update(final Movie movie) {
        return this.pgMovieService.update(movie)
                .flatMap(pgMovie -> {
                            Event event = new Event();
                            event.setAction(Action.UPDATE_MOVIE);
                            event.setMovie(this.movieMapper.toEntity(pgMovie));
                            this.kafkaProducer.send(event);
                            return Mono.just(pgMovie);
                        }
                );
    }

    @Override
    public Mono<Void> delete(final Long movieId) {
        EsMovie movie = new EsMovie();
        movie.setId(movieId);
        Event event = new Event();
        event.setAction(Action.DELETE_MOVIE);
        event.setMovie(movie);
        this.kafkaProducer.send(event);
        return this.pgMovieService.delete(movieId);
    }

}
