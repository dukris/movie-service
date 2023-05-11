package com.solvd.movie.service.impl;

import com.solvd.movie.kafka.producer.KafkaProducer;
import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.Event;
import com.solvd.movie.model.PgMovie;
import com.solvd.movie.model.exception.ResourceNotFoundException;
import com.solvd.movie.persistence.PgMovieRepository;
import com.solvd.movie.service.PgMovieService;
import com.solvd.movie.web.dto.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PgMovieServiceImpl implements PgMovieService {

    private final PgMovieRepository pgMovieRepository;
    private final KafkaProducer kafkaProducer;
    private final MovieMapper movieMapper;

    @Override
    public Mono<PgMovie> retrieveById(final Long movieId) {
        return this.pgMovieRepository.findById(movieId)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .flatMap(optionalMovie -> {
                    if (optionalMovie.isEmpty()) {
                        return Mono.error(
                                new ResourceNotFoundException(
                                        "Movie with id = " + movieId
                                                + " doesn't exist!"
                                )
                        );
                    }
                    return Mono.justOrEmpty(optionalMovie);
                });
    }

    @Override
    public Mono<Boolean> isExist(final Long movieId) {
        return this.pgMovieRepository.existsById(movieId);
    }

    @Override
    @Transactional
    public Mono<PgMovie> create(final PgMovie movie) {
        return this.pgMovieRepository.save(movie)
                .flatMap(pgMovie -> {
                            Event event = new Event();
                            event.setAction(Event.Action.CREATE_MOVIE);
                            event.setMovie(this.movieMapper.toEntity(pgMovie));
                            this.kafkaProducer.send(event);
                            return Mono.just(pgMovie);
                        }
                );
    }

    @Override
    @Transactional
    public Mono<PgMovie> update(final PgMovie movie) {
        return this.pgMovieRepository.findById(movie.getId())
                .flatMap(foundMovie -> {
                    foundMovie.setName(movie.getName());
                    foundMovie.setDescription(movie.getDescription());
                    foundMovie.setYear(movie.getYear());
                    return this.pgMovieRepository.save(foundMovie)
                            .flatMap(pgMovie -> {
                                        Event event = new Event();
                                        event.setAction(
                                                Event.Action.UPDATE_MOVIE
                                        );
                                        event.setMovie(
                                                this.movieMapper.toEntity(
                                                        pgMovie
                                                )
                                        );
                                        this.kafkaProducer.send(event);
                                        return Mono.just(pgMovie);
                                    }
                            );
                });
    }

    @Override
    @Transactional
    public Mono<Void> delete(final Long movieId) {
        EsMovie movie = new EsMovie();
        movie.setId(movieId);
        Event event = new Event();
        event.setAction(Event.Action.DELETE_REVIEW);
        event.setMovie(movie);
        this.kafkaProducer.send(event);
        return this.pgMovieRepository.deleteById(movieId);
    }

}
