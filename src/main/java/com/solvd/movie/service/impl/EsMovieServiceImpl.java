package com.solvd.movie.service.impl;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.persistence.EsMovieRepository;
import com.solvd.movie.service.EsMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EsMovieServiceImpl implements EsMovieService {

    private static final String INDEX = "movies";
    private final ReactiveElasticsearchOperations operations;
    private final EsMovieRepository esMovieRepository;

    @Override
    public Flux<EsMovie> retrieveAllByCriteria(
            final SearchCriteria searchCriteria,
            final Pageable pageable) {
        Criteria criteria = new Criteria();
        if (searchCriteria.getName() != null) {
            criteria.and(Criteria.where("name")
                    .contains(searchCriteria.getName())
            );
        }
        if (searchCriteria.getYearFrom() != null
                && searchCriteria.getYearTo() != null) {
            criteria.and(Criteria.where("year")
                    .greaterThanEqual(searchCriteria.getYearFrom())
                    .lessThanEqual(searchCriteria.getYearTo())
            );
        }
        if (searchCriteria.getCountry() != null) {
            criteria.and(Criteria.where("country")
                    .is(searchCriteria.getCountry())
            );
        }
        if (searchCriteria.getGenres() != null
                && !searchCriteria.getGenres().isEmpty()) {
            criteria.and(Criteria.where("genre")
                    .in(searchCriteria.getGenres())
            );
        }
        if (searchCriteria.getLanguage() != null) {
            criteria.and(Criteria.where("language")
                    .is(searchCriteria.getLanguage())
            );
        }
        if (searchCriteria.getQuality() != null) {
            criteria.and(Criteria.where("quality")
                    .is(searchCriteria.getQuality())
            );
        }
        Query searchQuery = new CriteriaQuery(criteria)
                .setPageable(pageable);
        return this.operations
                .search(searchQuery,
                        EsMovie.class,
                        IndexCoordinates.of(INDEX))
                .map(SearchHit::getContent);
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
