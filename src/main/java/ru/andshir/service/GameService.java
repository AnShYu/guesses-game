package ru.andshir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andshir.controllers.dto.request.AddQuestionToGameDTO;
import ru.andshir.controllers.dto.request.AddGameDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.controllers.dto.response.GameStatusResponseDTO;
import ru.andshir.exceptions.GameNotReadyException;
import ru.andshir.mappers.GameMapper;
import ru.andshir.mappers.RoundMapper;
import ru.andshir.model.Game;
import ru.andshir.model.Question;
import ru.andshir.model.Round;
import ru.andshir.repository.GamesRepository;
import ru.andshir.repository.QuestionsRepository;
import ru.andshir.repository.RoundsRepository;
import ru.andshir.service.game.readiness.checker.GameReadinessChecker;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameMapper gameMapper;
    private final GamesRepository gamesRepository;
    private final RoundMapper roundMapper;
    private final RoundsRepository roundsRepository;
    private final QuestionsRepository questionsRepository;
    private final GameReadinessChecker gameReadinessChecker;

    @Transactional
    public GameResponseDTO saveGame(AddGameDTO addGameDTO) {
        Game game = gameMapper.addGameDtoToGame(addGameDTO);
        Game savedGame = gamesRepository.save(game);
        return gameMapper.gameToGameResponseDTO(savedGame);
    }

    @Transactional
    public GameResponseDTO addQuestionToGame(long gameId, AddQuestionToGameDTO addQuestionDTO) {
        Game game = gamesRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("No game with such ID"));
        Round round = roundMapper.addQuestionToGameDtoToRound(addQuestionDTO, gameId);
        Question question = questionsRepository.findById(addQuestionDTO.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("No question with such Id"));
        round.setQuestion(question);
        Round savedRound = roundsRepository.save(round);
        game.getRoundsWithQuestions().add(savedRound);
        Game savedGame = gamesRepository.save(game);
        return gameMapper.gameToGameResponseDTO(savedGame);
    }

    @Transactional
    public GameStatusResponseDTO getGameStatus(long gameId) {
        Game game = gamesRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("No game with such ID"));
        List<Round> roundsInGame = game.getRoundsWithQuestions();
        List<Question> questionsInGame = new ArrayList<>();
        for (Round round: roundsInGame) {
            questionsInGame.add(round.getQuestion());
        }
        return gameMapper.gameToGameStatusResponseDTO(game, questionsInGame);
    }

    @Transactional
    public void checkGameReadiness(long gameId) {
        Game game = gamesRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("No game with such ID"));

        if (!gameReadinessChecker.checkGameReadiness(game)) {
            throw new GameNotReadyException("Game is not ready");
        }
    }

}
