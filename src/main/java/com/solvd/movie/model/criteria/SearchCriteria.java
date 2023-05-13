package com.solvd.movie.model.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchCriteria {

    private String name;
    private Integer year;
    private String country;
    private String genre;
    private String language;
    private String quality;

}
