package com.solvd.movie.service.kafka.action;

import com.solvd.movie.model.Event;
import reactor.core.publisher.Mono;

public interface ActionHandler {

    Mono<Void> process(Event event);

}
