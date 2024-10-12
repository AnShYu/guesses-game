package ru.andshir.controllers;

import ru.andshir.controllers.dto.request.QuestionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.andshir.controllers.dto.response.QuestionResponseDTO;
import ru.andshir.service.QuestionService;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public QuestionResponseDTO saveQuestion(@RequestBody QuestionDTO questionDTO) {
        return questionService.saveQuestion(questionDTO);
    }

}
