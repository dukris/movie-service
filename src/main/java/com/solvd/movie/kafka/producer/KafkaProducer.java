package com.solvd.movie.kafka.producer;

import com.solvd.movie.model.Event;
import com.solvd.movie.kafka.parser.Parser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaSender<String, Event> kafkaSender;
    private final Parser parser;

    public void send(final Event event) {
        this.kafkaSender.send(Mono.just(SenderRecord.create(
                        this.parser.getValue("producer.xml", "topic"),
                        1,
                        System.currentTimeMillis(),
                        UUID.randomUUID().toString(),
                        event,
                        null))
                )
                .subscribe();
    }

}
