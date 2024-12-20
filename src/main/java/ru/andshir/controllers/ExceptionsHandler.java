package ru.andshir.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.andshir.controllers.dto.response.RoundResultsNotReadyResponseDTO;
import ru.andshir.exceptions.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionsHandler {

    //TODO поменять исключения на более точные
    @ExceptionHandler
    public ResponseEntity<String> handleIllegalArgument(final IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<RoundResultsNotReadyResponseDTO> handleRoundResultsNotReady(final RoundResultsNotReadyException ex) {
        RoundResultsNotReadyResponseDTO roundResultsNotReadyResponseDTO = new RoundResultsNotReadyResponseDTO();
        roundResultsNotReadyResponseDTO.setNumberOfMissingAnswers(ex.getNumberOfMissingAnswers());
        roundResultsNotReadyResponseDTO.setMessage(ex.getMessage());
        return new ResponseEntity<>(roundResultsNotReadyResponseDTO, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleGameNotReady(final GameNotReadyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNoMoreRounds(final NoMoreRoundsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    //TODO какой статус запроса?
    @ExceptionHandler
    public ResponseEntity<String> handleNotFinalRound(final NotFinalRoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleFinalRoundResultsNotSaved(final FinalRoundResultsNotSavedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

}
