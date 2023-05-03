package com.solvd.movie.web.controller;

import com.solvd.movie.domain.PgMovie;
import com.solvd.movie.domain.criteria.SearchCriteria;
import com.solvd.movie.kafka.KafkaProducer;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {

    @Value("${services.review-url}")
    private String reviewUrl;
    private final MovieService movieService;
    private final MovieMapper movieMapper;
    private final WebClient.Builder webClientBuilder;
    private final KafkaProducer kafkaProducer;
    private final SearchCriteriaMapper criteriaMapper;

    @GetMapping()
    public Flux<MovieDto> getAllByCriteria(final SearchCriteriaDto criteriaDto) {
        SearchCriteria criteria = criteriaMapper.toEntity(criteriaDto);
        Flux<PgMovie> movies = movieService.retrieveAllByCriteria(criteria);
        return movies.map(movieMapper::toDto);
    }

    @GetMapping("/{movieId}")
    public Mono<MovieDto> getById(@PathVariable final Long movieId) {
        Mono<PgMovie> movie = movieService.retrieveById(movieId);
        return movie.map(movieMapper::toDto);
    }

    @GetMapping("/exists/{movieId}")
    public Mono<Boolean> isExist(@PathVariable final Long movieId) {
        return movieService.isExist(movieId);
    }

    @GetMapping("/{movieId}/reviews")
    public Flux<ReviewDto> getReviews(@PathVariable final Long movieId) {
        return webClientBuilder.build()
                .get()
                .uri(reviewUrl + "?movieId={movieId}", movieId)
                .retrieve()
                .bodyToFlux(ReviewDto.class);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieDto> create(@Validated @RequestBody final MovieDto movieDto) {
        PgMovie movie = movieMapper.toEntity(movieDto);
        Mono<PgMovie> movieMono = movieService.create(movie);
        return movieMono.map(movieMapper::toDto);
    }

    @DeleteMapping("/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long movieId) {
        movieService.delete(movieId);
        kafkaProducer.send(movieId);
    }

}
