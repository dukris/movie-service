package integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

@SpringBootTest
@SuppressWarnings("JTCOP.RuleAllTestsHaveProductionClass")
public class ITCase {

    private static final ElasticsearchContainer container = new EsContainer().init();

    @DynamicPropertySource
    private static void properties(final DynamicPropertyRegistry registry) {
        new PropertyOf(
                registry,
                "spring.elasticsearch.rest.uris",
                "localhost"
        ).set();
    }

    @BeforeAll
    public static void start() {
        container.start();
    }

    @AfterAll
    public static void stop() {
        container.stop();
    }

}
