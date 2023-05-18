package com.solvd.movie.persistence.impl;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.persistence.SearchMovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class SearchMovieRepositoryImpl implements SearchMovieRepository {

    private static final String INDEX = "movies";
    private final ReactiveElasticsearchOperations operations;

    @Override
    public Flux<EsMovie> findAllByCriteria(final SearchCriteria searchCriteria,
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

}