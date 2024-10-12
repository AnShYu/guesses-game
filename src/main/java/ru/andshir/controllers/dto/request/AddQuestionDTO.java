package ru.andshir.controllers.dto.request;

import lombok.Data;

@Data
public class AddQuestionDTO {

    private long questionId;
    private int roundNumber;

}
