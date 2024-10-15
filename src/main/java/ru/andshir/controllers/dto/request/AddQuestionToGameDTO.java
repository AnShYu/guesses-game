package ru.andshir.controllers.dto.request;

import lombok.Data;

@Data
public class AddQuestionToGameDTO {

    private long questionId;
    private int roundNumber;

}
