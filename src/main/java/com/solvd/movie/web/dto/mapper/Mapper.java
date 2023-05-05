package com.solvd.movie.web.dto.mapper;

public interface Mapper<Entity, Dto>{

    Entity toEntity(Dto dto);


    Dto toDto(Entity entity);

}
