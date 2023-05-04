package com.solvd.movie.service.impl;

import com.solvd.movie.domain.EsMovie;
import com.solvd.movie.domain.PgMovie;
import com.solvd.movie.domain.criteria.SearchCriteria;
import com.solvd.movie.domain.exception.ResourceNotFoundException;
import com.solvd.movie.persistence.EsRepository;
import com.solvd.movie.persistence.PgRepository;
import com.solvd.movie.service.MovieService;
import com.solvd.movie.web.dto.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final PgRepository pgRepository;
    private final EsRepository esRepository;
    private final MovieMapper movieMapper;

    @Override
    public Flux<PgMovie> retrieveAllByCriteria(final SearchCriteria criteria) {
        Flux<EsMovie> movies;
        if (criteria.getName() != null && criteria.getYear() != null) {
            movies = esRepository.findAllByNameAndYear(
                    criteria.getName(), criteria.getYear()
            );
        } else if (criteria.getName() != null) {
            movies = esRepository.findAllByName(criteria.getName());
        } else if (criteria.getYear() != null) {
            movies = esRepository.findAllByYear(criteria.getYear());
        } else {
            movies = esRepository.findAll();
        }
        return movies.map(movieMapper::toEntity);
    }

    @Override
    public Mono<PgMovie> retrieveById(final Long movieId) {
        return pgRepository.findById(movieId)
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
                    return pgRepository.findById(movieId);
                });
    }

    @Override
    public Mono<Boolean> isExist(final Long movieId) {
        return pgRepository.existsById(movieId);
    }

    @Override
    public Mono<PgMovie> create(final PgMovie movie) {
        return pgRepository.save(movie)
                .flatMap(savedMovie -> esRepository.save(
                                movieMapper.toEntity(savedMovie))
                        .flatMap(savedEsMovie -> Mono.just(
                                movieMapper.toEntity(savedEsMovie))));
    }

    @Override
    public void delete(final Long movieId) {
        esRepository.deleteById(movieId).subscribe();
        pgRepository.deleteById(movieId).subscribe();
    }

}
