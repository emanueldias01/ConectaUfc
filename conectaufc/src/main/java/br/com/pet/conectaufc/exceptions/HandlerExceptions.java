package br.com.pet.conectaufc.exceptions;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Configuration
@RestControllerAdvice
public class HandlerExceptions {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO invalidFieldsException(InvalidFieldsException ex){
        return new ErrorDTO(
                ex.getException(),
                ex.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO resourceNotFoundException(BussinessException ex){
        return new ErrorDTO(
                ex.getException(),
                ex.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO resourceNotFoundException(EntityNotFoundException ex){
        return new ErrorDTO(
                ex.getException(),
                ex.getMessage()
        );
    }
}
