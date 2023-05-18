package com.solvd.movie.persistence.builder;

import com.solvd.movie.model.criteria.SearchCriteria;
import org.springframework.data.elasticsearch.core.query.Criteria;

public interface CriteriaBuilder {

    Criteria build(SearchCriteria searchCriteria);

}
