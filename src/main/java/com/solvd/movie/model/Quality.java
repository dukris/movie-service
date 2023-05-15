package com.solvd.movie.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
public class Quality {

    @Field(name = "gte")
    private Integer from;

    @Field(name = "lte")
    private Integer to;

}
