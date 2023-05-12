package com.solvd.movie.persistence;

import com.solvd.movie.model.EsMovie;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

public interface EsMovieRepository
        extends ReactiveElasticsearchRepository<EsMovie, Long> {

    Flux<EsMovie> findAllByName(String name);

    Flux<EsMovie> findAllByYear(Integer year);

    Flux<EsMovie> findAllByNameAndYear(String name, Integer year);

}