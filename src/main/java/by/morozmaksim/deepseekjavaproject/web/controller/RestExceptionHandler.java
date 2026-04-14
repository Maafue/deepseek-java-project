package by.morozmaksim.deepseekjavaproject.web.controller;

import by.morozmaksim.deepseekjavaproject.domain.exception.ExceptionBody;
import by.morozmaksim.deepseekjavaproject.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleResourceNotFoundException(ResourceNotFoundException e){
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMethodArgumentNotValidException (MethodArgumentNotValidException e){
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");

        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        HashMap<String, String> map = new HashMap<>();

        for (FieldError error : errors){
            map.put(error.getField(), error.getDefaultMessage());
        }
        exceptionBody.setErrors(map);

        return exceptionBody;
    }
}
