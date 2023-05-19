package integration;

import org.springframework.test.context.DynamicPropertyRegistry;

public class TestProperties {

    public void set(final DynamicPropertyRegistry registry){
        registry.add("spring.elasticsearch.rest.uris", () -> "localhost");
        registry.add("kafka.bootstrap-servers", () -> "localhost");
        registry.add("services.review-url", () -> "url");
    }

}
