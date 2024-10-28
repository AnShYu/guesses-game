package ru.andshir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.andshir.controllers.dto.request.AnswerDTO;
import ru.andshir.controllers.dto.response.CurrentQuestionResponseDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.controllers.dto.response.RoundResultsResponseDTO;
import ru.andshir.exceptions.RoundResultsNotReadyException;
import ru.andshir.exceptions.TeamHasAlreadyAnsweredException;
import ru.andshir.exceptions.TeamNotAdmittedException;
import ru.andshir.mappers.AnswerMapper;
import ru.andshir.mappers.GameMapper;
import ru.andshir.model.*;
import ru.andshir.repository.*;
import ru.andshir.service.round_results_determiners.RoundResultsDeterminer;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayService {

    private final CurrentRoundsRepository currentRoundsRepository;
    private final GamesRepository gamesRepository;
    private final GameMapper gameMapper;
    private final RoundsRepository roundsRepository;
    private final AnswersRepository answersRepository;
    private final AnswerMapper answerMapper;
    private final TeamsRepository teamsRepository;
    //TODO is it ok?
    @Qualifier("RRDhowManySameAnswers")
    private final RoundResultsDeterminer roundResultsDeterminer;

    public GameResponseDTO startGame(long gameId) {
        CurrentRound currentRound = new CurrentRound();
        currentRound.setGameId(gameId);
        currentRound.setCurrentRoundNumber(1);
        currentRoundsRepository.save(currentRound);
        Game startedGame = gamesRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("There is no game with such Id to start"));
        return gameMapper.gameToGameResponseDTO(startedGame);
    }
    
    public CurrentQuestionResponseDTO getCurrentQuestion(long gameId) {
        CurrentRound currentRound = getCurrentRound(gameId);
        int currentRoundNumber = currentRound.getCurrentRoundNumber();
        Round round = roundsRepository.findById(new RoundId(gameId, currentRoundNumber))
                .orElseThrow(() -> new IllegalArgumentException("No round with such Id"));
        Question currentQuestion = round.getQuestion();
        CurrentQuestionResponseDTO currentQuestionResponseDTO = new CurrentQuestionResponseDTO();
        currentQuestionResponseDTO.setQuestionText(currentQuestion.getQuestionText());
        return currentQuestionResponseDTO;
    }

    public void saveAnswer(long gameId, AnswerDTO answerDTO) {
        long teamId = answerDTO.getTeamId();
        //TODO
        if (!teamIsAdmittedToGame(gameId, teamId)) {
            throw new TeamNotAdmittedException("The answering team is not admitted to the game");
        }

        CurrentRound currentRound = getCurrentRound(gameId);
        int currentRoundNumber = currentRound.getCurrentRoundNumber();

        if (!teamDidNotAnswerYet(gameId, currentRoundNumber, teamId)) {
            throw new TeamHasAlreadyAnsweredException("Team has already answered in this round");
        }


        Answer answer = answerMapper.DtoToAnswer(answerDTO, gameId, currentRoundNumber);
        answersRepository.save(answer);
    }

    public RoundResultsResponseDTO getRoundResults(long gameId) {
        int numberOfTeams = teamsRepository.countByGameId(gameId);

        int currentRoundNumber = getCurrentRound(gameId).getCurrentRoundNumber();
        int numberOfAnswers = answersRepository.countByGameIdAndRoundNumber(gameId, currentRoundNumber);

        if (numberOfAnswers != numberOfTeams) {
            throw new RoundResultsNotReadyException("Waiting for all teams to answer");
        } else {
            // TODO нунжо сделать тесты
            return roundResultsDeterminer.determineRoundResults(gameId, currentRoundNumber);
        }
    }


    private CurrentRound getCurrentRound(long gameId) {
        return currentRoundsRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("No game with such Id"));
    }

    private boolean teamIsAdmittedToGame(long gameId, long teamId) {
        //TODO
        return true;
    }

    private boolean teamDidNotAnswerYet(long gameId, int currentRoundNumber, long teamId) {
        //TODO
        return true;
    }

}
