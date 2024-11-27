package ru.andshir.controllers;

import org.springframework.web.bind.annotation.*;
import ru.andshir.controllers.dto.request.AddQuestionDTO;
import lombok.RequiredArgsConstructor;
import ru.andshir.controllers.dto.response.AllQuestionsResponseDTO;
import ru.andshir.controllers.dto.response.QuestionResponseDTO;
import ru.andshir.service.QuestionService;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public QuestionResponseDTO saveQuestion(@RequestBody AddQuestionDTO addQuestionDTO) {
        return questionService.saveQuestion(addQuestionDTO);
    }

    @GetMapping
    public AllQuestionsResponseDTO getAllQuestions() {
        return questionService.getAllQuestions();
    }

}
