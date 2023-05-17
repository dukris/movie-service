package com.solvd.movie.model.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchCriteria {

    private String name;
    private Integer yearFrom;
    private Integer yearTo;
    private String country;
    private List<String> genres;
    private String language;
    private Integer quality;

}
