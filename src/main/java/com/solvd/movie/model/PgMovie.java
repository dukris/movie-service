package com.solvd.movie.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Movie entity for Postgresql
 */

@Data
@Table(name = "movies")
public class PgMovie {

    @Id
    private Long id;
    private String name;
    private String description;
    private Integer year;

}
