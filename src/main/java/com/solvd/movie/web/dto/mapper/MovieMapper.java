package com.solvd.movie.web.dto.mapper;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.Movie;
import com.solvd.movie.web.dto.MovieDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper extends ObjectMapper<Movie, MovieDto>{

    EsMovie toEntity(Movie entity);

}
