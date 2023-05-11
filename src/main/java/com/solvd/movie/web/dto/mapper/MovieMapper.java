package com.solvd.movie.web.dto.mapper;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.PgMovie;
import com.solvd.movie.web.dto.MovieDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper extends ObjectMapper<PgMovie, MovieDto>{

    EsMovie toEntity(PgMovie entity);

    MovieDto toDto(EsMovie entity);

}
