package com.solvd.movie.model.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchCriteria {

    private String name = "";
    private Integer yearFrom = 0;
    private Integer yearTo = Integer.MAX_VALUE;
    private String country = "";
    private String genre = "";
    private String language = "";
    private Integer quality = 360;

}
