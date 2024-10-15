package ru.andshir.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.andshir.controllers.dto.request.AddQuestionToGameDTO;
import ru.andshir.controllers.dto.request.AddGameDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
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

}
