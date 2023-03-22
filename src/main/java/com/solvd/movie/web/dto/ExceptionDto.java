package com.solvd.movie.web.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public record ExceptionDto(

        String field,
        String message

) {
}
