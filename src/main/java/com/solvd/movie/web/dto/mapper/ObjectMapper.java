package com.solvd.movie.web.dto.mapper;

public interface ObjectMapper<Entity, Dto>{

    Entity toEntity(Dto dto);

    Dto toDto(Entity entity);

}
