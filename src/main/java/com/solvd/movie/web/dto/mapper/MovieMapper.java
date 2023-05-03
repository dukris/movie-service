package com.solvd.movie.web.dto.mapper;

import com.solvd.movie.domain.EsMovie;
import com.solvd.movie.domain.PgMovie;
import com.solvd.movie.web.dto.MovieDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    PgMovie toEntity(MovieDto dto);

    EsMovie toEntity(PgMovie movie);

    PgMovie toEntity(EsMovie movie);

    MovieDto toDto(PgMovie entity);

}
