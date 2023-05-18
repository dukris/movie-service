package com.solvd.movie.persistence.field;

import com.solvd.movie.model.criteria.SearchCriteria;
import org.springframework.data.elasticsearch.core.query.Criteria;

public interface Field {

    void apply(SearchCriteria searchCriteria, Criteria criteria);

}
