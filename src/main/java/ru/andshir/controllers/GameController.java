package ru.andshir.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.andshir.controllers.dto.request.AddQuestionDTO;
import ru.andshir.controllers.dto.request.GameDTO;
import ru.andshir.service.GameService;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/new_game")
    public long saveGame(@RequestBody GameDTO gameDTO) { // обычно всё-таки возвращают не ID, а объект DTO типа GameResponseDTO
       return gameService.saveGame(gameDTO);
    }

    @PostMapping("/{gameId}/add_question")
    public void addQuestionToGame(@PathVariable long gameId, @RequestBody AddQuestionDTO addQuestionDTO) {
        long questionId = addQuestionDTO.getQuestionId();
        gameService.addQuestionToGame(gameId, questionId);
    }

}
