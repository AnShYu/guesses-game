package ru.andshir.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.andshir.controllers.dto.request.AddQuestionDTO;
import ru.andshir.controllers.dto.request.GameDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.mappers.GameMapper;
import ru.andshir.model.Game;
import ru.andshir.service.GameService;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;

    @PostMapping("/new_game")
    public GameResponseDTO saveGame(@RequestBody GameDTO gameDTO) {
       Game savedGame = gameService.saveGame(gameDTO);
       return gameMapper.gameToGameResposeDTO(savedGame);
    }

    @PostMapping("/{gameId}/add_question")
    public void addQuestionToGame(@PathVariable long gameId, @RequestBody AddQuestionDTO addQuestionDTO) {
        long questionId = addQuestionDTO.getQuestionId();
        gameService.addQuestionToGame(gameId, questionId);
    }

}
