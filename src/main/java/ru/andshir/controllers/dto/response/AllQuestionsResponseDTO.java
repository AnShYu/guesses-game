package ru.andshir.controllers.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AllQuestionsResponseDTO {

    private List<QuestionResponseDTO> questionResponseDTOs = new ArrayList<>();

}
