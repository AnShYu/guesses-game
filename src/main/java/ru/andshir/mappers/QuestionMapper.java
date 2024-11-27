package ru.andshir.mappers;

import ru.andshir.controllers.dto.request.AddQuestionDTO;
import ru.andshir.controllers.dto.response.AllQuestionsResponseDTO;
import ru.andshir.controllers.dto.response.QuestionResponseDTO;
import ru.andshir.model.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public AllQuestionsResponseDTO questionsListToAllQuestionsResponseDTO(List<Question> questions) {
        List<QuestionResponseDTO> questionResponseDTOs = new ArrayList<>();
        for (Question question: questions) {
            QuestionResponseDTO questionResponseDTO = questionToQuestionResponseDTO(question);
            questionResponseDTOs.add(questionResponseDTO);
        }
        AllQuestionsResponseDTO allQuestionsResponseDTO = new AllQuestionsResponseDTO();
        allQuestionsResponseDTO.setQuestionResponseDTOs(questionResponseDTOs);
        return allQuestionsResponseDTO;
    }

}
