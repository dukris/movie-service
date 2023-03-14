package com.solvd.movie.web.controller;

import com.solvd.movie.domain.Movie;
import com.solvd.movie.kafka.KafkaProducer;
import com.solvd.movie.service.MovieClient;
import com.solvd.movie.web.dto.MovieDto;
import com.solvd.movie.web.dto.ReviewDto;
import com.solvd.movie.web.dto.mapper.MovieMapper;
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
    private final String REVIEW_URL;
    private final MovieClient movieClient;
    private final MovieMapper movieMapper;
    private final WebClient.Builder webClientBuilder;
    private final KafkaProducer kafkaProducer;

    @GetMapping()
    public Flux<MovieDto> getAll() {
        Flux<Movie> movies = movieClient.retrieveAll();
        return movies.map(movieMapper::toDto);
    }

    @GetMapping("/{movieId}")
    public Mono<MovieDto> getById(@PathVariable Long movieId) {
        Mono<Movie> movie = movieClient.retrieveById(movieId);
        return movie.map(movieMapper::toDto);
    }

    @GetMapping("/exists/{movieId}")
    public Mono<Boolean> isExist(@PathVariable Long movieId) {
        return movieClient.isExist(movieId);
    }

    @GetMapping("/{movieId}/reviews")
    public Flux<ReviewDto> getReviews(@PathVariable Long movieId) {
        return webClientBuilder.build()
                .get()
                .uri(REVIEW_URL + "?movieId={movieId}", movieId)
                .retrieve()
                .bodyToFlux(ReviewDto.class);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieDto> create(@Validated @RequestBody MovieDto movieDto) {
        Movie movie = movieMapper.toEntity(movieDto);
        Mono<Movie> movieMono = movieClient.create(movie);
        return movieMono.map(movieMapper::toDto);
    }

    @DeleteMapping("/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long movieId) {
        movieClient.delete(movieId);
        kafkaProducer.send(movieId);
    }

}
