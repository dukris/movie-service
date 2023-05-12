package com.solvd.movie.service.kafka;

import com.solvd.movie.model.Event;
import reactor.core.publisher.Mono;

public interface EventHandler {

    Mono<Void> receive(Event event);

}
