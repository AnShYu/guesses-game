package ru.andshir.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andshir.controllers.dto.request.AnswerDTO;
import ru.andshir.controllers.dto.response.CurrentQuestionResponseDTO;
import ru.andshir.controllers.dto.response.GameResponseDTO;
import ru.andshir.controllers.dto.response.GameResultsResponseDTO;
import ru.andshir.controllers.dto.response.RoundResultsResponseDTO;
import ru.andshir.exceptions.*;
import ru.andshir.mappers.AnswerMapper;
import ru.andshir.mappers.GameMapper;
import ru.andshir.mappers.GameResultsMapper;
import ru.andshir.mappers.RoundResultsMapper;
import ru.andshir.model.*;
import ru.andshir.repository.*;
import ru.andshir.service.game.results.calculator.GameResultsCalculator;
import ru.andshir.service.round.results.determiners.HowManySameAnswersDeterminer;
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
    private final HowManySameAnswersDeterminer howManySameAnswersDeterminer;
    private final RoundResultsMapper roundResultsMapper;
    private final RoundResultsRepository roundResultsRepository;
    //Todo правильно?
    private final GameResultsCalculator gameResultsCalculator;
    private final GameResultsMapper gameResultsMapper;

    @Transactional
    public GameResponseDTO startGame(long gameId) {
        CurrentRound currentRound = new CurrentRound();
        currentRound.setGameId(gameId);
        currentRound.setCurrentRoundNumber(1);
        currentRound.setResultsSaved(false);
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
    public RoundResultsResponseDTO endRound(long gameId) {
        roundResultsAreReady(gameId);
        int currentRoundNumber = getCurrentRound(gameId).getCurrentRoundNumber();
        RoundResultsWrapper roundResultsWrapper = howManySameAnswersDeterminer
                .determineRoundResults(gameId, currentRoundNumber);
        Map<Long, String> teamNameByTeamId = makeTeamNameByTeamIdMap(gameId);

        for (Long teamId: roundResultsWrapper.getPointsByTeamId().keySet()) {
            RoundResultId roundResultId = new RoundResultId(gameId, currentRoundNumber, teamId);
            int points = roundResultsWrapper.getPointsByTeamId().get(teamId);
            RoundResult roundResult = new RoundResult();
            roundResult.setRoundResultId(roundResultId);
            roundResult.setPoints(points);
            roundResultsRepository.save(roundResult);
        }
        //todo можно ли как-то по-другому менять?
        currentRoundsRepository.updateResultsSaved(gameId, true);

        RoundResultsResponseDTO roundResultsResponseDTO = roundResultsMapper
                .wrapperToDTO(roundResultsWrapper, teamNameByTeamId);

        roundResultsResponseDTO.setFinalRound(checkIfRoundWasFinal(gameId, currentRoundNumber));
        return roundResultsResponseDTO;
    }

    @Transactional
    public RoundResultsResponseDTO getRoundResults(long gameId) {
        roundResultsAreReady(gameId);
        int currentRoundNumber = getCurrentRound(gameId).getCurrentRoundNumber();
        RoundResultsWrapper roundResultsWrapper = howManySameAnswersDeterminer
                .determineRoundResults(gameId, currentRoundNumber);
        Map<Long, String> teamNameByTeamId = makeTeamNameByTeamIdMap(gameId);
        return roundResultsMapper
                .wrapperToDTO(roundResultsWrapper, teamNameByTeamId);
    }

    @Transactional
    public void startNextRound(long gameId) {
        CurrentRound currentRound = getCurrentRound(gameId);
        int currentRoundNumber = currentRound.getCurrentRoundNumber();
        if (checkIfRoundWasFinal(gameId, currentRoundNumber)) {
            throw new NoMoreRoundsException("Final round is over. No more rounds");
        }
        //TOdo поменять на получение сущности, изменение и сохранение
        currentRoundsRepository.updateRoundNumber(gameId, currentRoundNumber + 1);
        currentRoundsRepository.updateResultsSaved(gameId, false);
    }

    @Transactional
    public GameResultsResponseDTO getGameResults(long gameId) {
        int currentRoundNumber = getCurrentRound(gameId).getCurrentRoundNumber();
        if (!checkIfRoundWasFinal(gameId, currentRoundNumber)) {
            throw new NotFinalRoundException("It was not the final round");
        } else if (!checkIfRoundResultsAreSaved(gameId)) {
            throw new FinalRoundResultsNotSavedException("Final round results have not been saved yet");
        }
        List<RoundResult> allGameRoundResults = roundResultsRepository.findAllByGameId(gameId);

        Map<Long, Integer> totalTeamPointsByTeamId = gameResultsCalculator.calculateGameResults(allGameRoundResults);
        Map<Long, String> teamNameByTeamId = makeTeamNameByTeamIdMap(gameId);
        GameResultsResponseDTO gameResultsResponseDTO = gameResultsMapper.teamNameAndTeamIdAndPointsToGameResultsResponseDTO(totalTeamPointsByTeamId, teamNameByTeamId);
        return gameResultsResponseDTO;

    }

    @Transactional
    public GameResultsResponseDTO endGame(long gameId) {
        //Todo сохранять результаты игры, обнулять currentRound

        return new GameResultsResponseDTO();

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

    private void roundResultsAreReady(long gameId) {
        int numberOfTeams = teamsRepository.countByGameId(gameId);

        int currentRoundNumber = getCurrentRound(gameId).getCurrentRoundNumber();
        int numberOfAnswers = answersRepository.countByGameIdAndRoundNumber(gameId, currentRoundNumber);

        if (numberOfAnswers != numberOfTeams) {
            int numberOfMissingAnswers = numberOfTeams - numberOfAnswers;
            throw new RoundResultsNotReadyException("Waiting for all teams to answer", numberOfMissingAnswers);
        }
    }

    private Map<Long, String> makeTeamNameByTeamIdMap(long gameId) {
        Map<Long, String> teamNameByTeamId = new HashMap<>();
        List<Team> teams = teamsRepository.findTeamByGameId(gameId);
        for (Team team: teams) {
            teamNameByTeamId.put(team.getId(), team.getTeamName());
        }
        return teamNameByTeamId;
    }

    private boolean checkIfRoundWasFinal(long gameId, int currentRoundNumber) {
        Game game = gamesRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("There is no game with such Id"));
        int totalNumberOfRoundsInGame = game.getNumberOfRounds();
        return currentRoundNumber == totalNumberOfRoundsInGame;
    }

    private boolean checkIfRoundResultsAreSaved(long gameId) {
        CurrentRound currentRound = getCurrentRound(gameId);
        return currentRound.isResultsSaved();
    }




}
