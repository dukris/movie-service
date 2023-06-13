package com.solvd.movie.web.controller;

import com.solvd.movie.model.Movie;
import com.solvd.movie.model.fake.FkMovie;
import integration.GraphqlConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

@GraphQlTest(MovieController.class)
@Import(GraphqlConfig.class)
public class MovieQueryTest {

    @Autowired
    private GraphQlTester tester;

    @Test
    public void verifyGetAllByCriteria() {
        this.tester.documentName("query")
                .execute()
                .path("getAllByCriteria")
                .entityList(Movie.class)
                .hasSize(1)
                .satisfies(movies -> {
                    Assertions.assertNotNull(movies);
                    Assertions.assertFalse(movies.isEmpty());
                });
    }

    @Test
    public void verifyGetById() {
        this.tester.documentName("query")
                .execute()
                .path("getById")
                .entity(Movie.class)
                .satisfies(movie -> {
                    Assertions.assertNotNull(movie);
                    Assertions.assertEquals(
                            new FkMovie().getName(),
                            movie.getName()
                    );
                });
    }

}
