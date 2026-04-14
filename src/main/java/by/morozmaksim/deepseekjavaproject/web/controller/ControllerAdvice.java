package by.morozmaksim.deepseekjavaproject.web.controller;

import by.morozmaksim.deepseekjavaproject.domain.exception.ExceptionBody;
import by.morozmaksim.deepseekjavaproject.domain.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ExceptionBody handleResourceNotFoundException(ResourceNotFoundException e){
        return new ExceptionBody(e.getMessage());
    }
}
