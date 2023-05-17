package com.solvd.movie.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Movie entity for Elasticsearch
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "movies")
public class EsMovie {

    @Id
    private Long id;
    private String name;
    private Integer year;
    private String country;
    private String genre;
    private String language;

    @Field(type = FieldType.Integer_Range)
    private Quality quality;

}
