package com.solvd.movie.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@ToString
@Document(indexName = "movies")
@NoArgsConstructor
public class EsMovie {

    @Id
    private Long id;
    private String name;
    private String description;
    private Integer year;

}
