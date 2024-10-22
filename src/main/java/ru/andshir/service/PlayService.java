package ru.andshir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andshir.controllers.dto.request.AnswerDTO;
import ru.andshir.controllers.dto.response.CurrentQuestionResponseDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.mappers.AnswerMapper;
import ru.andshir.mappers.GameMapper;
import ru.andshir.model.*;
import ru.andshir.repository.AnswersRepository;
import ru.andshir.repository.CurrentRoundsRepository;
import ru.andshir.repository.GamesRepository;
import ru.andshir.repository.RoundsRepository;

@Service
@RequiredArgsConstructor
public class PlayService {

    private final CurrentRoundsRepository currentRoundsRepository;
    private final GamesRepository gamesRepository;
    private final GameMapper gameMapper;
    private final RoundsRepository roundsRepository;
    private final AnswersRepository answersRepository;
    private final AnswerMapper answerMapper;

    public GameResponseDTO startGame(long gameId) {
        CurrentRound currentRound = new CurrentRound();
        currentRound.setGameId(gameId);
        currentRound.setCurrentRound(1);
        currentRoundsRepository.save(currentRound);
        Game startedGame = gamesRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("There is no game with such Id to start"));
        return gameMapper.gameToGameResponseDTO(startedGame);
    }

    public CurrentQuestionResponseDTO getCurrentQuestion(long gameId) {
        CurrentRound currentRound = currentRoundsRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find current round for the game with such Id"));
        int currentRoundNumber = currentRound.getCurrentRound();
        Round round = roundsRepository.findById(new RoundId(gameId, currentRoundNumber))
                .orElseThrow(() -> new IllegalArgumentException("No round with such Id"));
        Question currentQuestion = round.getQuestion();
        CurrentQuestionResponseDTO currentQuestionResponseDTO = new CurrentQuestionResponseDTO();
        currentQuestionResponseDTO.setQuestionText(currentQuestion.getQuestionText());
        return currentQuestionResponseDTO;
    }

    public void saveAnswer(long gameId, AnswerDTO answerDTO) {
        CurrentRound currentRound = currentRoundsRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find current round for the game with such Id"));
        int roundNumber = (currentRound.getCurrentRound());
        Answer answer = answerMapper.DtoToAnswer(answerDTO, gameId, roundNumber);
        answersRepository.save(answer);
    }

}
