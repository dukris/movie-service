package com.solvd.movie.kafka.consumer;

import com.solvd.movie.model.Event;
import com.solvd.movie.service.kafka.EventHandler;
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
    private final EventHandler eventHandler;

    @PostConstruct
    public void receive() {
        this.kafkaReceiver.receive()
                .subscribe(record -> {
                    ReceiverOffset offset = record.receiverOffset();
                    Event event = record.value();
                    log.info("Received event: " + event.toString());
                    this.eventHandler.receive(event).subscribe();
                    offset.acknowledge();
                });
    }

}
