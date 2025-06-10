package com.damian34.notes.infrastructure.config.exception;

import com.damian34.notes.domain.exception.NoteException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @GraphQlExceptionHandler
    public GraphQLError handleNoteException(NoteException ex) {
        log.error("NoteException occurred: {}", ex.getMessage());
        return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .errorType(ErrorType.BAD_REQUEST)
                .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("Validation error occurred: {}", ex.getMessage());
        return GraphqlErrorBuilder.newError()
                .message(ex.getConstraintViolations().stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", ")))
                .errorType(ErrorType.BAD_REQUEST)
                .build();
    }
}
