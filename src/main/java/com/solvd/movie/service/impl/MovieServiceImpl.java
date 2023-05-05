package com.solvd.movie.service.impl;

import com.solvd.movie.domain.EsMovie;
import com.solvd.movie.domain.Event;
import com.solvd.movie.domain.PgMovie;
import com.solvd.movie.domain.criteria.SearchCriteria;
import com.solvd.movie.domain.exception.ResourceNotFoundException;
import com.solvd.movie.kafka.producer.KafkaProducer;
import com.solvd.movie.persistence.EsRepository;
import com.solvd.movie.persistence.PgRepository;
import com.solvd.movie.service.MovieService;
import com.solvd.movie.web.dto.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final PgRepository pgRepository;
    private final EsRepository esRepository;
    private final MovieMapper movieMapper;
    private final KafkaProducer kafkaProducer;

    @Override
    public Flux<PgMovie> retrieveAllByCriteria(final SearchCriteria criteria) {
        Flux<EsMovie> movies;
        if (criteria.getName() != null && criteria.getYear() != null) {
            movies = this.esRepository.findAllByNameAndYear(
                    criteria.getName(),
                    criteria.getYear()
            );
        } else if (criteria.getName() != null) {
            movies = this.esRepository.findAllByName(criteria.getName());
        } else if (criteria.getYear() != null) {
            movies = this.esRepository.findAllByYear(criteria.getYear());
        } else {
            movies = this.esRepository.findAll();
        }
        return movies.map(this.movieMapper::toEntity);
    }

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
    @Transactional
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
//        return pgRepository.save(movie)
//                .flatMap(savedMovie -> esRepository.save(
//                                movieMapper.toEntity(savedMovie))
//                        .flatMap(savedEsMovie -> Mono.just(
//                                movieMapper.toEntity(savedEsMovie))));
    }

    @Override
    @Transactional
    public Mono<EsMovie> replicateCreate(EsMovie movie) {
        return this.esRepository.save(movie);
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
        return this.pgRepository.deleteById(movieId);
    }

    @Override
    @Transactional
    public Mono<Void> replicateDelete(Long movieId) {
        return this.esRepository.deleteById(movieId);
    }

}
