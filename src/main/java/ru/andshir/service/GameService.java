package ru.andshir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andshir.controllers.dto.request.AddQuestionToGameDTO;
import ru.andshir.controllers.dto.request.AddGameDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.mappers.GameMapper;
import ru.andshir.mappers.RoundMapper;
import ru.andshir.model.Game;
import ru.andshir.model.Question;
import ru.andshir.model.Round;
import ru.andshir.repository.GameRepository;
import ru.andshir.repository.QuestionsRepository;
import ru.andshir.repository.RoundsRepository;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameMapper gameMapper;
    private final GameRepository gameRepository;
    private final RoundMapper roundMapper;
    private final RoundsRepository roundsRepository;
    private final QuestionsRepository questionsRepository;

    public GameResponseDTO saveGame(AddGameDTO addGameDTO) {
        Game game = gameMapper.addGameDtoToGame(addGameDTO);
        Game savedGame = gameRepository.save(game);
        return gameMapper.gameToGameResponseDTO(savedGame);
    }


    public GameResponseDTO addQuestionToGame(long gameId, AddQuestionToGameDTO addQuestionDTO) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("No game with such ID"));
        Round round = roundMapper.addQuestionToGameDtoToRound(addQuestionDTO, gameId);
        Question question = questionsRepository.findById(addQuestionDTO.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("No question with such Id"));
        round.setQuestion(question);
        Round savedRound = roundsRepository.save(round);
        game.getQuestionsInRounds().add(savedRound);
        Game savedGame = gameRepository.save(game);
        return gameMapper.gameToGameResponseDTO(savedGame);
    }

    public GameResponseDTO getGameStatus(long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("No game with such ID"));
        return gameMapper.gameToGameResponseDTO(game);
    }

}
