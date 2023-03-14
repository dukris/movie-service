package com.solvd.movie.kafka;

import com.solvd.movie.kafka.parser.XmlParser;
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

    private final KafkaSender<String, Long> kafkaSender;

    public void send(Long message) {
        kafkaSender.send(Mono.just(SenderRecord.create(
                        XmlParser.getValue("topic"),
                        2,
                        System.currentTimeMillis(),
                        UUID.randomUUID().toString(),
                        message,
                        null)))
                .subscribe();
    }

}
