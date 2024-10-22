package ru.andshir.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.andshir.controllers.dto.request.AnswerDTO;
import ru.andshir.controllers.dto.response.CurrentQuestionResponseDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.service.PlayService;

@RestController
@RequestMapping("/play")
@RequiredArgsConstructor
public class PlayController {

    private final PlayService playService;

    @PostMapping("/{gameId}/start_game")
    public GameResponseDTO startGame(@PathVariable long gameId) {
        return playService.startGame(gameId);
    }

    @GetMapping("/{gameId}/current_question")
    public CurrentQuestionResponseDTO getCurrentQuestion(@PathVariable long gameId) {
        return playService.getCurrentQuestion(gameId);
    }

    @PostMapping("/{gameId}/answer")
    public void saveAnswer(@PathVariable long gameId, @RequestBody AnswerDTO answerDTO){
        playService.saveAnswer(gameId, answerDTO);
    }

}
