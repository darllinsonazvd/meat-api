package io.github.darllinsonazvd.sales.api.controller;

import io.github.darllinsonazvd.sales.api.ApiErrors;
import io.github.darllinsonazvd.sales.exception.HandleException;
import io.github.darllinsonazvd.sales.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    @ExceptionHandler(HandleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleException(HandleException e) {
        return new ApiErrors(e.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleOrderNotFoundException(OrderNotFoundException e) {
        return new ApiErrors(e.getMessage());
    }
}
