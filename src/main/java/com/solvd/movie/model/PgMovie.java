package com.solvd.movie.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
public class PgMovie {

    @Id
    private Long id;
    private String name;
    private String description;
    private Integer year;

}
