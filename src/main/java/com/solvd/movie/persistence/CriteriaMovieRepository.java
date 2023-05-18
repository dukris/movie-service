package com.solvd.movie.persistence;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.criteria.SearchCriteria;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;

public interface CriteriaMovieRepository {

    Flux<EsMovie> findAllByCriteria(
            SearchCriteria searchCriteria,
            Pageable pageable
    );

}