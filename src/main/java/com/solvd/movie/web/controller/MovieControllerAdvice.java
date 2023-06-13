package com.solvd.movie.web.controller;

import com.solvd.movie.model.exception.ResourceNotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import io.github.eocqrs.rs.RsError;
import io.github.eocqrs.rs.json.JsonError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
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
    public String handleResourceNotFoundException(
            final ResourceNotFoundException ex) {
        return new JsonError(
                new RsError.WithCode(
                        ex.getMessage(),
                        404
                )
        ).content();
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIOException(final IOException ex) {
        return new JsonError(
                new RsError.WithCode(
                        ex.getMessage(),
                        400
                )
        ).content();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new JsonError(
                                new RsError.WithCode(
                                        error.getDefaultMessage(),
                                        400
                                )
                        ).content()
                )
                .toList();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleOtherExceptions(final Exception ex) {
        log.error(ex.getMessage(), ex);
        return new JsonError(
                new RsError.WithCode(
                        "Please, try later!",
                        503
                )
        ).content();
    }

    @GraphQlExceptionHandler
    public GraphQLError handle(final ResourceNotFoundException ex) {
        return GraphqlErrorBuilder.newError()
                .errorType(ErrorType.NOT_FOUND)
                .message(ex.getMessage())
                .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handle(final Exception ex){
        return GraphqlErrorBuilder.newError()
                .errorType(ErrorType.INTERNAL_ERROR)
                .message(ex.getMessage())
                .build();
    }

}
