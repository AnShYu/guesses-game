package ru.andshir.service;

import ru.andshir.controllers.dto.request.QuestionDTO;
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

    public void saveQuestion(QuestionDTO questionDTO) {
        Question question = questionMapper.dtoToQuestion(questionDTO);
        questionsRepository.save(question);
    }

}
