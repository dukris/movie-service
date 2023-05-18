package com.solvd.movie.persistence;

import com.solvd.movie.model.EsMovie;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface EsMovieRepository
        extends ReactiveElasticsearchRepository<EsMovie, Long>,
        CriteriaMovieRepository {

}
