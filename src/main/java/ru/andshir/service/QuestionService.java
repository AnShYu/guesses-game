package ru.andshir.service;

import ru.andshir.controllers.dto.request.QuestionDTO;
import ru.andshir.controllers.dto.response.QuestionResponseDTO;
import ru.andshir.model.Question;
import lombok.RequiredArgsConstructor;
import ru.andshir.mappers.QuestionMapper;
import org.springframework.stereotype.Service;
import ru.andshir.repository.QuestionsRepository;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionMapper questionMapper;
    private final QuestionsRepository questionsRepository;

    public QuestionResponseDTO saveQuestion(QuestionDTO questionDTO) {
        Question question = questionMapper.questionDtoToQuestion(questionDTO);
        Question savedQuestion = questionsRepository.save(question);
        return questionMapper.questionToQuestionResponseDTO(savedQuestion);
    }

}
