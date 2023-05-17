package com.solvd.movie.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Full movie entity
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {

    @Id
    private Long id;
    private String name;
    private Integer year;
    private String country;
    private String genre;
    private String language;
    private String description;

    @Column("quality_from")
    private Integer qualityFrom;

    @Column("quality_to")
    private Integer qualityTo;

}
