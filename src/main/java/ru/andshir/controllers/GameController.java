package ru.andshir.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.andshir.controllers.dto.request.AddQuestionToGameDTO;
import ru.andshir.controllers.dto.request.AddGameDTO;
import ru.andshir.controllers.dto.request.GameReadinessCheckRequestDTO;
import ru.andshir.controllers.dto.response.GameReadyResponseDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.controllers.dto.response.GameStatusResponseDTO;
import ru.andshir.service.GameService;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/add_game")
    public GameResponseDTO saveGame(@RequestBody AddGameDTO addGameDTO) {
       return gameService.saveGame(addGameDTO);
    }

    @PostMapping("/{gameId}/add_question")
    public GameResponseDTO addQuestionToGame(@PathVariable long gameId, @RequestBody AddQuestionToGameDTO addQuestionDTO) {
        return gameService.addQuestionToGame(gameId, addQuestionDTO);
    }

    @GetMapping("/{gameId}/status")
    public GameStatusResponseDTO getGameStatus(@PathVariable long gameId) {
        return gameService.getGameStatus(gameId);
    }

    @GetMapping("/{gameId}/check")
    public GameReadyResponseDTO checkGameReadiness(@PathVariable long gameId, @RequestBody GameReadinessCheckRequestDTO gameReadinessCheckRequestDTO) {
        return gameService.checkGameReadiness(gameId, gameReadinessCheckRequestDTO);
    }
}
