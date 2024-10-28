package ru.andshir.service.round_results_determiners;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.andshir.controllers.dto.response.RoundResultsResponseDTO;
import ru.andshir.model.Answer;
import ru.andshir.model.Round;
import ru.andshir.model.Team;
import ru.andshir.repository.AnswersRepository;
import ru.andshir.repository.TeamsRepository;

import java.util.*;

@Component("RRDhowManySameAnswers")
@RequiredArgsConstructor
public class RRDhowManySameAnswers implements RoundResultsDeterminer {

    private final AnswersRepository answersRepository;
    private final TeamsRepository teamsRepository;

    @Override
    public RoundResultsResponseDTO determineRoundResults(long gameId, int currentRoundNumber) {
        String mostPopularAnswer;
        HashMap<String, Integer> teamPoints = new HashMap<>();

        List<Answer> answers = answersRepository.findByGameIdAndRoundNumber(gameId, currentRoundNumber);
        Map<String, Integer> answersPopularity = determineAnswersPopularity(answers);
        int maximumPoints = Collections.max(answersPopularity.values());

        mostPopularAnswer = determineMostPopularAnswer(answersPopularity, maximumPoints);

        //TODO мне не нравится, что так сложно собирается мапа команда-баллы
        teamPoints = determinePointsOfEachTeam(answers, answersPopularity);

        RoundResultsResponseDTO roundResultsResponseDTO = new RoundResultsResponseDTO();
        roundResultsResponseDTO.setMostPopularAnswer(mostPopularAnswer);
        roundResultsResponseDTO.setTeamPoints(teamPoints);

        return roundResultsResponseDTO;
    }

    //TODO что лучше возвращать map или hashmap?
    private Map<String, Integer> determineAnswersPopularity(List<Answer> answers) {
        Map<String, Integer> answersPopularity = new HashMap<>();
        for (Answer answer: answers) {
            String answerText = answer.getAnswer();
            if (answersPopularity.containsKey(answerText)) {
                int numberOfRepeats = answersPopularity.get(answerText);
                answersPopularity.put(answerText, numberOfRepeats + 1);
            } else {
                answersPopularity.put(answerText, 0);
            }
        }
        return answersPopularity;
    }

    private String determineMostPopularAnswer(Map<String, Integer> answersPopularity, int maximumPoints) {
        for (String answer: answersPopularity.keySet()) {
            if (answersPopularity.get(answer) == maximumPoints) {
                return answer;
            }
        }
        throw new IllegalArgumentException ("No answer with such amount of points");
    }

    //TODO возвращать ли Map или HashMap? кажется, hashMap нагляднее показывает вызывающему, что возвращается
    private HashMap<String, Integer> determinePointsOfEachTeam (List<Answer> answers, Map<String, Integer> answersPopularity) {
        HashMap<String, Integer> teamsPoints = new HashMap<>();

        for (Answer answer: answers) {
            String answerText = answer.getAnswer();
            long teamId = answer.getTeamId();
            Team team = teamsRepository.findById(teamId).
                    orElseThrow(() -> new IllegalArgumentException("No team with such ID"));
            int points = answersPopularity.get(answerText);
            teamsPoints.put(team.getTeamName(), points);
        }
        return teamsPoints;
    }

}
