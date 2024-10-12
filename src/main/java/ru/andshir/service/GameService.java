package ru.andshir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andshir.controllers.dto.request.AddQuestionDTO;
import ru.andshir.controllers.dto.request.GameDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.mappers.GameMapper;
import ru.andshir.mappers.RoundMapper;
import ru.andshir.model.Game;
import ru.andshir.model.Round;
import ru.andshir.repository.GameRepository;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameMapper gameMapper;
    private final GameRepository gameRepository;
    private final RoundMapper roundMapper;

    public GameResponseDTO saveGame(GameDTO gameDTO) {
        Game game = gameMapper.gameDtoToGame(gameDTO);
        Game savedGame = gameRepository.save(game);
        return gameMapper.gameToGameResponseDTO(savedGame);
    }


    public GameResponseDTO addQuestionToGame(long gameId, AddQuestionDTO addQuestionDTO) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("No game with such ID"));
        Round round = roundMapper.addQuestionDtoToRound(addQuestionDTO, gameId);
        game.getQuestionsInRounds().add(round);
        Game savedGame = gameRepository.save(game);
        return gameMapper.gameToGameResponseDTO(savedGame);
    }

}
