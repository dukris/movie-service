package com.solvd.movie.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "movies")
@NoArgsConstructor
public class PgMovie {

    @Id
    private Long id;
    private String name;
    private String description;
    private Integer year;

}
