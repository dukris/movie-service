package com.solvd.movie.web.controller;

import com.solvd.movie.model.Movie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.service.MovieService;
import com.solvd.movie.web.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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

    @Value("${services.review-url}")
    private String reviewUrl;

    @GetMapping
    @QueryMapping
    public Flux<Movie> getAllByCriteria(
            @Argument final SearchCriteria criteria) {
        return this.movieService.retrieveAllByCriteria(
                criteria,
                PageRequest.of(0, 20)
        );
    }

    @GetMapping("/{movieId}")
    @QueryMapping
    public Mono<Movie> getById(
            @PathVariable @Argument final Long movieId) {
        return this.movieService.retrieveById(movieId);
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

    @PostMapping
    @MutationMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Movie> create(
            @Validated @RequestBody @Argument final Movie movie) {
        return this.movieService.create(movie);
    }

    @PutMapping("/{movieId}")
    @MutationMapping
    public Mono<Movie> update(@PathVariable @Argument final Long movieId,
                                 @Validated @Argument
                                 @RequestBody final Movie movie) {
        movie.setId(movieId);
        return this.movieService.update(movie);
    }

    @DeleteMapping("/{movieId}")
    @MutationMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable @Argument final Long movieId) {
        return this.movieService.delete(movieId);
    }

}
