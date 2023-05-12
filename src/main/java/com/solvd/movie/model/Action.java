package com.solvd.movie.model;

import lombok.Getter;

@Getter
public enum Action {

    CREATE_MOVIE("createMovie"),
    UPDATE_MOVIE("updateMovie"),
    DELETE_MOVIE("deleteMovie"),
    DELETE_REVIEW("deleteReview");

    private final String name;

    Action(final String name) {
        this.name = name;
    }

}