package com.solvd.movie.service.kafka.impl;

import com.solvd.movie.model.Event;
import com.solvd.movie.service.kafka.action.ActionHandler;
import com.solvd.movie.service.kafka.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EventHandlerImpl implements EventHandler {

    private final Map<String, ActionHandler> actions;

    @Override
    public Mono<Void> receive(final Event event) {
        return this.actions.get(event.getAction().getName()).process(event);
    }

}
