package ru.andshir.mappers;

import org.springframework.stereotype.Component;
import ru.andshir.controllers.dto.request.GameDTO;
import ru.andshir.model.Game;

@Component
public class GameMapper {

    public Game dtoToGame(GameDTO gameDTO) {
        Game game = new Game();
        game.setGameDate(gameDTO.getGameDate());
        return game;
    }

}
