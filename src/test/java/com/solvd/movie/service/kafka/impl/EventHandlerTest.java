package com.solvd.movie.service.kafka.impl;

import com.solvd.movie.model.Action;
import com.solvd.movie.model.Event;
import com.solvd.movie.model.fake.FkEsMovie;
import com.solvd.movie.service.EsMovieService;
import com.solvd.movie.service.kafka.action.ActionHandler;
import com.solvd.movie.service.kafka.action.impl.CreateMovieHandler;
import com.solvd.movie.service.kafka.action.impl.DeleteMovieHandler;
import com.solvd.movie.service.kafka.action.impl.UpdateMovieHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class EventHandlerTest {

    @Mock
    private Map<String, ActionHandler> actions;

    @Mock
    private EsMovieService esMovieService;

    @InjectMocks
    private EventHandlerImpl eventHandler;

    @Test
    public void verifiesCreateMovieAction() {
        Event event = new Event();
        event.setAction(Action.CREATE_MOVIE);
        event.setMovie(new FkEsMovie());
        Mockito.when(this.actions.get(event.getAction().getName()))
                .thenReturn(new CreateMovieHandler(this.esMovieService));
        Mockito.when(this.esMovieService.create(event.getMovie()))
                .thenReturn(Mono.just(event.getMovie()));
        Mono<Void> result = this.eventHandler.receive(event);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(this.esMovieService,
                Mockito.times(1)).create(event.getMovie());
    }

    @Test
    public void verifiesDeleteMovieAction() {
        Event event = new Event();
        event.setAction(Action.DELETE_MOVIE);
        event.setMovie(new FkEsMovie());
        Mockito.when(this.actions.get(event.getAction().getName()))
                .thenReturn(new DeleteMovieHandler(this.esMovieService));
        Mockito.when(this.esMovieService.delete(event.getMovie().getId()))
                .thenReturn(Mono.empty());
        Mono<Void> result = this.eventHandler.receive(event);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(this.esMovieService,
                Mockito.times(1)).delete(event.getMovie().getId());
    }

    @Test
    public void verifiesUpdateMovieAction() {
        Event event = new Event();
        event.setAction(Action.UPDATE_MOVIE);
        event.setMovie(new FkEsMovie());
        Mockito.when(this.actions.get(event.getAction().getName()))
                .thenReturn(new UpdateMovieHandler(this.esMovieService));
        Mockito.when(this.esMovieService.update(event.getMovie()))
                .thenReturn(Mono.just(event.getMovie()));
        Mono<Void> result = this.eventHandler.receive(event);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(this.esMovieService,
                Mockito.times(1)).update(event.getMovie());
    }

}
