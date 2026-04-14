package by.morozmaksim.deepseekjavaproject.web.controller;

import by.morozmaksim.deepseekjavaproject.domain.exception.ExceptionBody;
import by.morozmaksim.deepseekjavaproject.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleResourceNotFoundException(ResourceNotFoundException e){
        return new ExceptionBody(e.getMessage());
    }
}
