package ru.andshir.service;

import org.springframework.transaction.annotation.Transactional;
import ru.andshir.controllers.dto.request.AddQuestionDTO;
import ru.andshir.controllers.dto.response.AllQuestionsResponseDTO;
import ru.andshir.controllers.dto.response.QuestionResponseDTO;
import ru.andshir.model.Question;
import lombok.RequiredArgsConstructor;
import ru.andshir.mappers.QuestionMapper;
import org.springframework.stereotype.Service;
import ru.andshir.repository.QuestionsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionMapper questionMapper;
    private final QuestionsRepository questionsRepository;

    @Transactional
    public QuestionResponseDTO saveQuestion(AddQuestionDTO addQuestionDTO) {
        Question question = questionMapper.addQuestionDtoToQuestion(addQuestionDTO);
        Question savedQuestion = questionsRepository.save(question);
        return questionMapper.questionToQuestionResponseDTO(savedQuestion);
    }

    @Transactional
    public AllQuestionsResponseDTO getAllQuestions() {
        List<Question> allQuestions = questionsRepository.findAll();
        return questionMapper.questionsListToAllQuestionsResponseDTO(allQuestions);
    }

}
