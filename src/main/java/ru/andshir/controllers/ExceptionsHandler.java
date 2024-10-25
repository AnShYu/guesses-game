package ru.andshir.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    //TODO roundsResultsNotReady
    @ExceptionHandler
    public ResponseEntity<String> handleAll(final IllegalArgumentException ex) {
        return new ResponseEntity<>("что-то пошло не так; " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
