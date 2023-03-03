package com.solvd.movie.web.dto.mapper;

import com.solvd.movie.domain.Movie;
import com.solvd.movie.web.dto.MovieDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie toEntity(MovieDto movieDto);

    MovieDto toDto(Movie movie);

    List<MovieDto> toDto(List<Movie> movies);

}
