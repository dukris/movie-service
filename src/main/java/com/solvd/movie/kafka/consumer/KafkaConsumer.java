package com.solvd.movie.kafka.consumer;

import com.solvd.movie.domain.Event;
import com.solvd.movie.service.MovieService;
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
    private final MovieService movieService;

    @PostConstruct
    private void receive() {
        this.kafkaReceiver.receive()
//                .flatMap(record -> {
//                    ReceiverOffset offset = record.receiverOffset();
//                    Event event = record.value();
//                    log.info("Received event: " + event.toString());
//                    if (event.getAction() != null
//                            && event.getAction() == Event.Action.CREATE_MOVIE) {
//                        return this.movieService.replicateCreate(event.getMovie())
//                                .flatMap(movie -> {
//                                    offset.acknowledge();
//                                    return Mono.just(movie);
//                                });
//                    }
//                    offset.acknowledge();
//                    return Mono.just(record);
//                }).then();
                .subscribe(record -> {
                    ReceiverOffset offset = record.receiverOffset();
                    log.info("Received event: " + record.value().toString());
                    if (record.value().getAction() != null
                            && record.value().getAction() == Event.Action.CREATE_MOVIE) {
                        this.movieService.replicateCreate(record.value().getMovie()).subscribe();
                    }
                    offset.acknowledge();
                });
    }

}
