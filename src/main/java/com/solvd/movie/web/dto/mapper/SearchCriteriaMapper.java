package com.solvd.movie.web.dto.mapper;

import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.web.dto.criteria.SearchCriteriaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchCriteriaMapper {

    SearchCriteria toEntity(SearchCriteriaDto dto);

    SearchCriteriaDto toDto(SearchCriteria entity);

}
