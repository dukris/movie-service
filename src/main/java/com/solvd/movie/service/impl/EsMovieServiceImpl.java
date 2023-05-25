package com.solvd.movie.service.impl;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.persistence.EsMovieRepository;
import com.solvd.movie.service.EsMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EsMovieServiceImpl implements EsMovieService {

    private final EsMovieRepository esMovieRepository;

    @Override
    public Flux<EsMovie> retrieveAllByCriteria(
            final SearchCriteria searchCriteria,
            final Pageable pageable) {
        return this.esMovieRepository.findAllByCriteria(
                searchCriteria,
                pageable
        );
    }

    @Override
    @Transactional
    public Mono<EsMovie> create(final EsMovie movie) {
        return this.esMovieRepository.save(movie);
    }

    @Override
    @Transactional
    public Mono<EsMovie> update(final EsMovie movie) {
        return this.esMovieRepository.findById(movie.getId())
                .flatMap(foundMovie -> {
                    foundMovie.setName(movie.getName());
                    foundMovie.setYear(movie.getYear());
                    foundMovie.setCountry(movie.getCountry());
                    foundMovie.setGenre(movie.getGenre());
                    foundMovie.setLanguage(movie.getLanguage());
                    foundMovie.setQuality(movie.getQuality());
                    return this.esMovieRepository.save(movie);
                });
    }

    @Override
    @Transactional
    public Mono<Void> delete(final Long movieId) {
        return this.esMovieRepository.deleteById(movieId);
    }

}
