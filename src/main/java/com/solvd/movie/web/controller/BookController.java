package com.solvd.movie.web.controller;

import com.solvd.movie.domain.Movie;
import com.solvd.movie.service.MovieService;
import com.solvd.movie.web.dto.MovieDto;
import com.solvd.movie.web.dto.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class BookController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @GetMapping()
    public List<MovieDto> getAll() {
        List<Movie> movies = movieService.retrieveAll();
        return movieMapper.toDto(movies);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto create(@Validated @RequestBody MovieDto movieDto) {
        Movie movie = movieMapper.toEntity(movieDto);
        movie = movieService.create(movie);
        return movieMapper.toDto(movie);
    }

}
