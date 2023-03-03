package com.solvd.movie.service.impl;

import com.solvd.movie.domain.Movie;
import com.solvd.movie.persistence.MovieRepository;
import com.solvd.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<Movie> retrieveAll() {
        return movieRepository.findAll();
    }

    @Override
    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

}
