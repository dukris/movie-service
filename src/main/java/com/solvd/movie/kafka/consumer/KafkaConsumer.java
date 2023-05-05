package com.solvd.movie.kafka.consumer;

import com.solvd.movie.model.Event;
import com.solvd.movie.model.exception.IllegalActionException;
import com.solvd.movie.service.EsMovieService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final KafkaReceiver<String, Event> kafkaReceiver;
    private final EsMovieService esMovieService;

    @PostConstruct
    private void receive() {
        this.kafkaReceiver.receive()
                .subscribe(record -> {
                    ReceiverOffset offset = record.receiverOffset();
                    Event event = record.value();
                    log.info("Received event: " + event.toString());
                    switch (event.getAction()) {
                        case CREATE_MOVIE -> this.esMovieService.create(
                                event.getMovie()
                        ).subscribe();
                        case UPDATE_MOVIE -> this.esMovieService.update(
                                event.getMovie()
                        ).subscribe();
                        case DELETE_MOVIE -> this.esMovieService.delete(
                                event.getMovie().getId()
                        ).subscribe();
                        default -> throw new IllegalActionException(
                                "Wrong action!"
                        );
                    }
                    offset.acknowledge();
                });
    }

}
