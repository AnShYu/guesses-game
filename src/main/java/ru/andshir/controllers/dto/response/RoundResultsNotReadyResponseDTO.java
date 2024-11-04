package ru.andshir.controllers.dto.response;

import lombok.Data;

@Data
public class RoundResultsNotReadyResponseDTO {

    private String message;
    private int numberOfMissingAnswers;

}
