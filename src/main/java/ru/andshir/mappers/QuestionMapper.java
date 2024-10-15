package ru.andshir.mappers;

import ru.andshir.controllers.dto.request.AddQuestionDTO;
import ru.andshir.controllers.dto.response.QuestionResponseDTO;
import ru.andshir.model.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public Question addQuestionDtoToQuestion(AddQuestionDTO addQuestionDTO) {
        Question question = new Question();
        question.setQuestionText(addQuestionDTO.getQuestionText());
        return question;
    }

    public QuestionResponseDTO questionToQuestionResponseDTO(Question question) {
        QuestionResponseDTO questionResponseDTO = new QuestionResponseDTO();
        questionResponseDTO.setId(question.getId());
        questionResponseDTO.setQuestionText(question.getQuestionText());
        return questionResponseDTO;
    }

}
