package com.solvd.movie.web.dto.mapper;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.PgMovie;
import com.solvd.movie.web.dto.MovieDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    PgMovie toEntity(MovieDto dto);

    EsMovie toEntity(PgMovie entity);

    MovieDto toDto(EsMovie entity);

    MovieDto toDto(PgMovie entity);

}
