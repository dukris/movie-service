package com.solvd.movie.persistence.builder.impl;

import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.persistence.builder.CriteriaBuilder;
import com.solvd.movie.persistence.field.Field;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CriteriaBuilderImpl implements CriteriaBuilder {

    private final List<Field> fields;

    public Criteria build(final SearchCriteria searchCriteria) {
        Criteria criteria = new Criteria();
        this.fields.forEach(field -> field.apply(searchCriteria, criteria));
        return criteria;
    }

}
