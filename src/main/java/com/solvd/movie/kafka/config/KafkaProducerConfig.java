package com.solvd.movie.kafka.config;

import com.solvd.movie.kafka.parser.XmlParser;
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
public class KafkaProducerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public SenderOptions<String, Long> senderOptions() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, XmlParser.getValue("keySerializer"));
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, XmlParser.getValue("valueSerializer"));
        return SenderOptions.create(props);
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public KafkaSender<String, Long> kafkaSender() {
        return KafkaSender.create(senderOptions());
    }

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder.name(XmlParser.getValue("topic"))
                .partitions(6)
                .replicas(1)
                .build();
    }

}
