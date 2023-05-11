package com.solvd.movie.kafka.producer;

import com.solvd.movie.model.Event;
import com.solvd.movie.kafka.parser.Parser;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final Parser parser;

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;
    private String filename;

    @Bean
    public Map<String, Object> senderOptions() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                this.bootstrapServers
        );
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                this.parser.getValue(this.filename, "keySerializer")
        );
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                this.parser.getValue(this.filename, "valueSerializer")
        );

        return props;
    }

    @Bean
    public KafkaSender<String, Event> kafkaSender() {
        this.filename = "producer.xml";
        return KafkaSender.create(SenderOptions.create(this.senderOptions()));
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(
                        this.parser.getValue("producer.xml", "topic"))
                .build();
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
                this.bootstrapServers);
        return new KafkaAdmin(configs);
    }

}
