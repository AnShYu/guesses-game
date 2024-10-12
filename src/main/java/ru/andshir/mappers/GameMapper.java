package ru.andshir.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.andshir.controllers.dto.request.GameDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.controllers.dto.response.RoundResponseDTO;
import ru.andshir.model.Game;
import ru.andshir.model.Round;

@Component
@RequiredArgsConstructor
public class GameMapper {

    private final RoundMapper roundMapper;

    public Game gameDtoToGame(GameDTO gameDTO) {
        Game game = new Game();
        game.setGameDate(gameDTO.getGameDate());
        return game;
    }

    public GameResponseDTO gameToGameResponseDTO(Game game) {
        GameResponseDTO gameResponseDTO = new GameResponseDTO();
        gameResponseDTO.setId(game.getId());
        gameResponseDTO.setGameDate(game.getGameDate());
        for (Round round: game.getQuestionsInRounds()) {
            RoundResponseDTO roundResponseDTO = roundMapper.roundToRoundResponseDTO(round);
            gameResponseDTO.getQuestionsInRounds().add(roundResponseDTO);
        }
        return gameResponseDTO;
    }
}
