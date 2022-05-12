package com.test.experimentality.web;

import com.test.experimentality.web.errors.BadRequestException;
import com.test.experimentality.web.errors.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorDTO> requestExceptionHandler(BadRequestException ex){
        return new ResponseEntity<>(ErrorDTO.builder().code(ex.getCode()).message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }
}
