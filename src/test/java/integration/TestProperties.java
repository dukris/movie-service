package integration;

import org.springframework.test.context.DynamicPropertyRegistry;

public class TestProperties {

    public void set(final DynamicPropertyRegistry registry){
        registry.add("spring.elasticsearch.rest.uris", () -> "localhost");
        registry.add("kafka.bootstrap-servers", () -> "localhost");
//        registry.add("spring.r2dbc.url", () -> "localhost");
//        registry.add("spring.r2dbc.username", () -> "test");
//        registry.add("spring.r2dbc.password", () -> "test");
    }

}
