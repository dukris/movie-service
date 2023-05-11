package com.solvd.movie.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Movie entity for Elasticsearch
 */

@Data
@Document(indexName = "movies")
public class EsMovie {

    @Id
    private Long id;
    private String name;
    private String description;
    private Integer year;

}
