package ru.andshir.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/{gameId}/current_question")
    public CurrentQuestionResponseDTO getCurrentQuestion(@PathVariable long gameId) {
        return playService.getCurrentQuestion(gameId);
    }

}
