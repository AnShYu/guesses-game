package ru.andshir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andshir.controllers.dto.response.CurrentQuestionResponseDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.mappers.GameMapper;
import ru.andshir.mappers.QuestionMapper;
import ru.andshir.model.*;
import ru.andshir.repository.CurrentRoundRepository;
import ru.andshir.repository.GameRepository;
import ru.andshir.repository.QuestionsRepository;
import ru.andshir.repository.RoundsRepository;

@Service
@RequiredArgsConstructor
public class PlayService {

    private final CurrentRoundRepository currentRoundRepository;
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final RoundsRepository roundsRepository;
    private final QuestionMapper questionMapper;

    public GameResponseDTO startGame(long gameId) {
        CurrentRound currentRound = new CurrentRound();
        currentRound.setGameId(gameId);
        currentRound.setCurrentRound(1);
        currentRoundRepository.save(currentRound);
        Game startedGame = gameRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("There is no game with such Id to start"));
        return gameMapper.gameToGameResponseDTO(startedGame);
    }

    public CurrentQuestionResponseDTO getCurrentQuestion(long gameId) {
        CurrentRound currentRound = currentRoundRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find current round for the game with such Id"));
        int currentRoundNumber = currentRound.getCurrentRound();
        Round round = roundsRepository.findById(new RoundId(gameId, currentRoundNumber))
                .orElseThrow(() -> new IllegalArgumentException("No round with such Id"));
        Question currentQuestion = round.getQuestion();
        CurrentQuestionResponseDTO currentQuestionResponseDTO = new CurrentQuestionResponseDTO();
        currentQuestionResponseDTO.setQuestionText(currentQuestion.getQuestionText());
        return currentQuestionResponseDTO;
    }

}
