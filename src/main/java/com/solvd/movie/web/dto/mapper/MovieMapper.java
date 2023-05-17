package com.solvd.movie.web.dto.mapper;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.Movie;
import com.solvd.movie.model.Quality;
import com.solvd.movie.web.dto.MovieDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper extends ObjectMapper<Movie, MovieDto>{

    @Mapping(target = "quality", expression = "java(mapToEntity(entity))")
    EsMovie toEntity(Movie entity);

    default Quality mapToEntity(Movie entity) {
        Quality quality = new Quality();
        quality.setFrom(entity.getQualityFrom());
        quality.setTo(entity.getQualityTo());
        return quality;
    }

}
