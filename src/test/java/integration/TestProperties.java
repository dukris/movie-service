package integration;

import org.springframework.test.context.DynamicPropertyRegistry;

public class TestProperties {

    public void set(final DynamicPropertyRegistry registry){
        registry.add("kafka.bootstrap-servers", () -> "localhost");
    }

}
