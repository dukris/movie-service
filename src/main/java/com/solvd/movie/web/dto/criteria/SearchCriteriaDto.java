package com.solvd.movie.web.dto.criteria;

import lombok.Data;

@Data
public class SearchCriteriaDto {

    private String name = "";
    private Integer yearFrom = 0;
    private Integer yearTo = Integer.MAX_VALUE;
    private String country = "";
    private String genre = "";
    private String language = "";
    private Integer quality = 360;

}