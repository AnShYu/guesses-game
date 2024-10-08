package ru.andshir.mappers;

import ru.andshir.controllers.dto.request.QuestionDTO;
import ru.andshir.model.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public Question dtoToQuestion (QuestionDTO questionDTO) {
        Question question = new Question();
        question.setQuestionText(questionDTO.getQuestionText());
        return question;
    }
}
