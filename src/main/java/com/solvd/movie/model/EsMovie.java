package com.solvd.movie.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "movies")
@NoArgsConstructor
@AllArgsConstructor
public class EsMovie {

    @Id
    private Long id;
    private String name;
    private String description;
    private Integer year;

}
