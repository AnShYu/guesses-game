package ru.andshir.mappers;

import org.springframework.stereotype.Component;
import ru.andshir.controllers.dto.request.GameDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.model.Game;

@Component
public class GameMapper {

    public Game gameDtoToGame(GameDTO gameDTO) {
        Game game = new Game();
        game.setGameDate(gameDTO.getGameDate());
        return game;
    }

    public GameResponseDTO gameToGameResposeDTO(Game game) {
        GameResponseDTO gameResponseDTO = new GameResponseDTO();
        gameResponseDTO.setId(game.getId());
        gameResponseDTO.setGameDate(game.getGameDate());
        gameResponseDTO.setGameQuestions(game.getGameQuestions());
        return gameResponseDTO;
    }

}
