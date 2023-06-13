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
@SuppressWarnings("JTCOP.RuleAllTestsHaveProductionClass")
public class MovieMutationTest {

    @Autowired
    private GraphQlTester tester;

    @Test
    public void verifiesCreate() {
        this.tester.documentName("mutation")
                .execute()
                .path("data.create")
                .entity(Movie.class)
                .satisfies(movie -> {
                    Assertions.assertNotNull(movie);
                    Assertions.assertEquals(
                            new FkMovie().getName(),
                            movie.getName()
                    );
                });
    }

    @Test
    public void verifiesUpdate() {
        this.tester.documentName("mutation")
                .execute()
                .path("data.update")
                .entity(Movie.class)
                .satisfies(movie -> {
                    Assertions.assertNotNull(movie);
                    Assertions.assertEquals(
                            new FkMovie().getName(),
                            movie.getName()
                    );
                });
    }

    @Test
    public void verifiesDelete() {
        this.tester.documentName("mutation")
                .execute()
                .path("data.delete")
                .valueIsNull();
    }

}
