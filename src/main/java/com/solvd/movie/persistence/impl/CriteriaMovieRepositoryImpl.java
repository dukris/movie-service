package com.solvd.movie.persistence.impl;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.persistence.CriteriaMovieRepository;
import com.solvd.movie.persistence.builder.impl.CriteriaBuilderImpl;
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
public class CriteriaMovieRepositoryImpl implements CriteriaMovieRepository {

    private static final String INDEX = "movies";
    private final CriteriaBuilderImpl builder;
    private final ReactiveElasticsearchOperations operations;

    @Override
    public Flux<EsMovie> findAllByCriteria(final SearchCriteria searchCriteria,
                                           final Pageable pageable) {
        Criteria criteria = this.builder.build(searchCriteria);
        Query searchQuery = new CriteriaQuery(criteria)
                .setPageable(pageable);
        return this.operations
                .search(searchQuery,
                        EsMovie.class,
                        IndexCoordinates.of(INDEX))
                .map(SearchHit::getContent);
    }

}