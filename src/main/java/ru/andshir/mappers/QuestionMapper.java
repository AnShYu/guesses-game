package ru.andshir.mappers;

import ru.andshir.controllers.dto.request.QuestionDTO;
import ru.andshir.controllers.dto.response.QuestionResponseDTO;
import ru.andshir.model.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public Question questionDtoToQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setQuestionText(questionDTO.getQuestionText());
        return question;
    }

    public QuestionResponseDTO questionToQuestionResponseDTO(Question question) {
        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
        questionResponseDTO.setId(question.getId());
        questionResponseDTO.setQuestionText(question.getQuestionText());
        return questionResponseDTO;
    }

}
