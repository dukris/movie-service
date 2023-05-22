package integration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;


@Configuration
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        R2dbcAutoConfiguration.class
})
@ComponentScan(basePackages = "com.solvd.movie.persistence")
@EnableReactiveElasticsearchRepositories(basePackages = "com.solvd.movie.persistence")
public class TestConfig {
}
