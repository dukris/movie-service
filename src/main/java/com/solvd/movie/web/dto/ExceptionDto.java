package com.solvd.movie.web.dto;

import lombok.Builder;

@Builder
public record ExceptionDto(

        String field,
        String message

) {
}
