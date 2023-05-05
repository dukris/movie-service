package com.solvd.movie.service.impl;

import com.solvd.movie.kafka.producer.KafkaProducer;
import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.Event;
import com.solvd.movie.model.PgMovie;
import com.solvd.movie.model.exception.ResourceNotFoundException;
import com.solvd.movie.persistence.PgRepository;
import com.solvd.movie.service.PgMovieService;
import com.solvd.movie.web.dto.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PgMovieServiceImpl implements PgMovieService {

    private final PgRepository pgRepository;
    private final KafkaProducer kafkaProducer;
    private final MovieMapper movieMapper;

    @Override
    public Mono<PgMovie> retrieveById(final Long movieId) {
        return this.pgRepository.findById(movieId)
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
                    return this.pgRepository.findById(movieId);
                });
    }

    @Override
    public Mono<Boolean> isExist(final Long movieId) {
        return this.pgRepository.existsById(movieId);
    }

    @Override
    public Mono<PgMovie> create(final PgMovie movie) {
        return this.pgRepository.save(movie)
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
    public Mono<PgMovie> update(final PgMovie movie) {
        return this.pgRepository.save(movie)
                .flatMap(pgMovie -> {
                            Event event = new Event();
                            event.setAction(Event.Action.UPDATE_MOVIE);
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
        event.setAction(Event.Action.DELETE_REVIEW);
        event.setMovie(movie);
        this.kafkaProducer.send(event);
        return this.pgRepository.deleteById(movieId);
    }

}
