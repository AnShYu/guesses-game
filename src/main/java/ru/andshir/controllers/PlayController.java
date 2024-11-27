package ru.andshir.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.andshir.controllers.dto.request.AnswerDTO;
import ru.andshir.controllers.dto.response.CurrentQuestionResponseDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.controllers.dto.response.GameResultsResponseDTO;
import ru.andshir.controllers.dto.response.RoundResultsResponseDTO;
import ru.andshir.service.GameService;
import ru.andshir.service.PlayService;

@RestController
@RequestMapping("/play")
@RequiredArgsConstructor
public class PlayController {

    private final PlayService playService;
    private final GameService gameService;

    @PostMapping("/{gameId}/start_game")
    public GameResponseDTO startGame(@PathVariable long gameId) {
        gameService.checkGameReadiness(gameId);
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

    @PostMapping("/{gameId}/end_round")
    public RoundResultsResponseDTO endRound(@PathVariable long gameId) {
        return playService.endRound(gameId);
    }

    @GetMapping("/{gameId}/round_results")
    public RoundResultsResponseDTO getRoundResults(@PathVariable long gameId) {
        return playService.getRoundResults(gameId);
    }

    @PostMapping("/{gameId}/next_round")
    public void startNextRound(@PathVariable long gameId) {
        playService.startNextRound(gameId);
    }

    @GetMapping("/{gameId}/game_results")
    public GameResultsResponseDTO getGameResults(@PathVariable long gameId) {
        return playService.getGameResults(gameId);
    }

//    @PostMapping("/{gameId}/end_game")
//    public GameResultsResponseDTO getGameResults(@PathVariable long gameId) {
//        return playService.endGame(gameId);
//    }

}
