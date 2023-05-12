package com.solvd.movie.service.kafka.action.impl;

import com.solvd.movie.model.Event;
import com.solvd.movie.service.EsMovieService;
import com.solvd.movie.service.kafka.action.ActionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("deleteMovie")
@RequiredArgsConstructor
public class DeleteMovieHandler implements ActionHandler {

    private final EsMovieService esMovieService;

    @Override
    public Mono<Void> process(final Event event) {
        return this.esMovieService.delete(event.getMovie().getId()).then();
    }

}
