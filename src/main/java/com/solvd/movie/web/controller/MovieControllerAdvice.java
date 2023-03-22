package com.solvd.movie.web.controller;

import com.solvd.movie.domain.exception.ResourceNotFoundException;
import com.solvd.movie.web.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class MovieControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ExceptionDto(null, ex.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handleIOException(IOException ex) {
        return new ExceptionDto(null, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ExceptionDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ExceptionDto(error.getObjectName() + "." + error.getField(), error.getDefaultMessage()))
                .toList();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDto handleOtherExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ExceptionDto(null, "Please, try later!");
    }

}
