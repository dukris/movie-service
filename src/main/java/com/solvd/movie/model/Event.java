package com.solvd.movie.model;

import lombok.Data;

@Data
public class Event {

    private  Action action;
    private EsMovie movie;

    public enum Action {

        CREATE_MOVIE,
        UPDATE_MOVIE,
        DELETE_MOVIE,
        DELETE_REVIEW

    }

}
