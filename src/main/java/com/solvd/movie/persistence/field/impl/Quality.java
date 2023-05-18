package com.solvd.movie.persistence.field.impl;

import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.persistence.field.Field;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.stereotype.Component;

@Component
public class Quality implements Field {

    @Override
    public void apply(final SearchCriteria searchCriteria,
                      final Criteria criteria) {
        if (searchCriteria.getQuality() != null) {
            criteria.and(Criteria.where("quality")
                    .is(searchCriteria.getQuality())
            );
        }
    }

}
