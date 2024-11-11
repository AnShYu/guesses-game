package ru.andshir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andshir.controllers.dto.request.AnswerDTO;
import ru.andshir.controllers.dto.response.CurrentQuestionResponseDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.controllers.dto.response.RoundResultsResponseDTO;
import ru.andshir.exceptions.RoundResultsNotReadyException;
import ru.andshir.exceptions.TeamHasAlreadyAnsweredException;
import ru.andshir.exceptions.TeamNotAdmittedException;
import ru.andshir.mappers.AnswerMapper;
import ru.andshir.mappers.GameMapper;
import ru.andshir.mappers.RoundResultsMapper;
import ru.andshir.model.*;
import ru.andshir.repository.*;
import ru.andshir.service.round.results.determiners.RRDhowManySameAnswers;
import ru.andshir.service.round.results.determiners.RoundResultsWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final RRDhowManySameAnswers RRDhowManySameAnswers;
    private final RoundResultsMapper roundResultsMapper;
    private final RoundResultsRepository roundResultsRepository;

    @Transactional
    public GameResponseDTO startGame(long gameId) {
        CurrentRound currentRound = new CurrentRound();
        currentRound.setGameId(gameId);
        currentRound.setCurrentRoundNumber(1);
        currentRoundsRepository.save(currentRound);
        Game startedGame = gamesRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("There is no game with such Id to start"));
        return gameMapper.gameToGameResponseDTO(startedGame);
    }

    @Transactional
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

    @Transactional
    public void saveAnswer(long gameId, AnswerDTO answerDTO) {
        long teamId = answerDTO.getTeamId();
        if (!teamIsAdmittedToGame(gameId, teamId)) {
            throw new TeamNotAdmittedException("The answering team is not admitted to the game");
        }
        int currentRoundNumber = getCurrentRound(gameId).getCurrentRoundNumber();
        if (teamAlreadyAnswered(gameId, currentRoundNumber, teamId)) {
            throw new TeamHasAlreadyAnsweredException("Team has already answered in this round");
        }

        Answer answer = answerMapper.dtoToAnswer(answerDTO, gameId, currentRoundNumber);
        answersRepository.save(answer);
    }

    @Transactional
    public RoundResultsResponseDTO getRoundResults(long gameId) {
        int numberOfTeams = teamsRepository.countByGameId(gameId);

        int currentRoundNumber = getCurrentRound(gameId).getCurrentRoundNumber();
        int numberOfAnswers = answersRepository.countByGameIdAndRoundNumber(gameId, currentRoundNumber);

        if (numberOfAnswers != numberOfTeams) {
            int numberOfMissingAnswers = numberOfTeams - numberOfAnswers;
            throw new RoundResultsNotReadyException("Waiting for all teams to answer", numberOfMissingAnswers);
        } else {
            RoundResultsWrapper roundResultsWrapper = RRDhowManySameAnswers
                    .determineRoundResults(gameId, currentRoundNumber);
            Map<Long, String> teamNameByTeamId = new HashMap<>();
            List<Team> teams = teamsRepository.findTeamByGameId(gameId);
            for (Team team: teams) {
                teamNameByTeamId.put(team.getId(), team.getTeamName());
            }

            for (Long teamId: roundResultsWrapper.getPointsByTeamId().keySet()) {
                RoundResultId roundResultId = new RoundResultId(gameId, currentRoundNumber, teamId);
                int points = roundResultsWrapper.getPointsByTeamId().get(teamId);
                RoundResult roundResult = new RoundResult();
                roundResult.setRoundResultId(roundResultId);
                roundResult.setPoints(points);
                roundResultsRepository.save(roundResult);
            }

            return roundResultsMapper
                    .wrapperToDTO(roundResultsWrapper, teamNameByTeamId);
        }
    }


    private CurrentRound getCurrentRound(long gameId) {
        return currentRoundsRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("No game with such Id"));
    }

    private boolean teamIsAdmittedToGame(long gameId, long teamId) {
        Team team = teamsRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("No team with such Id"));
        return team.getGameId() == gameId;
    }

    private boolean teamAlreadyAnswered(long gameId, int currentRoundNumber, long teamId) {
        AnswerId answerId = new AnswerId();
        answerId.setTeamId(teamId);
        answerId.setGameId(gameId);
        answerId.setRoundNumber(currentRoundNumber);
        return answersRepository.existsById(answerId);
    }

}
