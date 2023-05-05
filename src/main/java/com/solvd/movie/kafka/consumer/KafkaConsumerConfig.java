package com.solvd.movie.kafka.consumer;

import com.solvd.movie.domain.Event;
import com.solvd.movie.kafka.parser.Parser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final Parser parser;

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ReceiverOptions<String, Event> receiverOptions() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, this.parser.getValue(
                "consumer.xml", "groupId")
        );
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, this.parser.getValue(
                "consumer.xml", "keyDeserializer")
        );
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, this.parser.getValue(
                "consumer.xml", "valueDeserializer")
        );
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, this.parser.getValue(
                "consumer.xml", "valueDefaultType")
        );
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        ReceiverOptions<String, Event> receiverOptions = ReceiverOptions.create(props);
        return receiverOptions
                .subscription(Collections.singleton(
                        this.parser.getValue("consumer.xml", "topic")))
                .addAssignListener(receiverPartitions ->
                        log.info("Assigned: " + receiverPartitions))
                .addRevokeListener(receiverPartitions ->
                        log.info("Revoked: " + receiverPartitions));
    }

    @Bean
    public KafkaReceiver<String, Event> kafkaReceiver() {
        return KafkaReceiver.create(this.receiverOptions());
    }

}
