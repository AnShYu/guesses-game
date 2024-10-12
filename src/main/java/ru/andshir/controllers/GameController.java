package ru.andshir.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.andshir.controllers.dto.request.AddQuestionDTO;
import ru.andshir.controllers.dto.request.GameDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.service.GameService;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/new_game")
    public GameResponseDTO saveGame(@RequestBody GameDTO gameDTO) {
       return gameService.saveGame(gameDTO);
    }

    @PostMapping("/{gameId}/add_question")
    public GameResponseDTO addQuestionToGame(@PathVariable long gameId, @RequestBody AddQuestionDTO addQuestionDTO) {
        return gameService.addQuestionToGame(gameId, addQuestionDTO);
    }

}
