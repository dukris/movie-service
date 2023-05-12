package com.solvd.movie.web.controller;

import com.solvd.movie.model.Movie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.service.MovieService;
import com.solvd.movie.web.dto.MovieDto;
import com.solvd.movie.web.dto.ReviewDto;
import com.solvd.movie.web.dto.criteria.SearchCriteriaDto;
import com.solvd.movie.web.dto.mapper.MovieMapper;
import com.solvd.movie.web.dto.mapper.SearchCriteriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;
    private final WebClient.Builder webClientBuilder;
    private final SearchCriteriaMapper criteriaMapper;
    private final MovieMapper movieMapper;

    @Value("${services.review-url}")
    private String reviewUrl;

    @GetMapping()
    public Flux<MovieDto> getAllByCriteria(
            final SearchCriteriaDto criteriaDto) {
        SearchCriteria criteria = this.criteriaMapper.toEntity(criteriaDto);
        Flux<Movie> movies = this.movieService.retrieveAllByCriteria(criteria);
        return movies.map(this.movieMapper::toDto);
    }

    @GetMapping("/{movieId}")
    public Mono<MovieDto> getById(@PathVariable final Long movieId) {
        Mono<Movie> movie = this.movieService.retrieveById(movieId);
        return movie.map(this.movieMapper::toDto);
    }

    @GetMapping("/exists/{movieId}")
    public Mono<Boolean> isExist(@PathVariable final Long movieId) {
        return this.movieService.isExist(movieId);
    }

    @GetMapping("/{movieId}/reviews")
    public Flux<ReviewDto> getReviews(@PathVariable final Long movieId) {
        return this.webClientBuilder.build()
                .get()
                .uri(this.reviewUrl + "?movieId={movieId}", movieId)
                .retrieve()
                .bodyToFlux(ReviewDto.class);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieDto> create(
            @Validated @RequestBody final MovieDto movieDto) {
        Movie movie = this.movieMapper.toEntity(movieDto);
        Mono<Movie> movieMono = this.movieService.create(movie);
        return movieMono.map(this.movieMapper::toDto);
    }

    @PutMapping("/{movieId}")
    public Mono<MovieDto> update(@PathVariable final Long movieId,
            @Validated @RequestBody final MovieDto movieDto) {
        Movie movie = this.movieMapper.toEntity(movieDto);
        movie.setId(movieId);
        Mono<Movie> movieMono = this.movieService.update(movie);
        return movieMono.map(this.movieMapper::toDto);
    }

    @DeleteMapping("/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable final Long movieId) {
        return this.movieService.delete(movieId);
    }

}
