package ru.andshir.service.round.results.determiners;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.andshir.model.Answer;
import ru.andshir.repository.AnswersRepository;

import java.util.*;

@Component
@RequiredArgsConstructor
public class HowManySameAnswersDeterminer implements RoundResultsDeterminer {

    private final AnswersRepository answersRepository;

    //TODO Нужно ли делать его transactional?
    @Override
    public RoundResultsWrapper determineRoundResults(long gameId, int currentRoundNumber) {
        List<Answer> answers = answersRepository.findByGameIdAndRoundNumber(gameId, currentRoundNumber);

        Map<String, Integer> answersPopularity = determineAnswersPopularity(answers);
        List<String> mostPopularAnswers = determineMostPopularAnswers(answersPopularity);
        Map<Long, Integer> teamIdPoints = determinePointsOfEachTeam(answers, answersPopularity);

        RoundResultsWrapper roundResultsWrapper = new RoundResultsWrapper();
        roundResultsWrapper.setMostPopularAnswers(mostPopularAnswers);
        roundResultsWrapper.setPointsByTeamId(teamIdPoints);

        return roundResultsWrapper;
    }

    private Map<String, Integer> determineAnswersPopularity(List<Answer> answers) {
        Map<String, Integer> answersPopularity = new HashMap<>();
        for (Answer answer: answers) {
            String answerText = answer.getAnswer();
            if (answersPopularity.containsKey(answerText)) {
                int numberOfRepeats = answersPopularity.get(answerText);
                answersPopularity.put(answerText, numberOfRepeats + 1);
            } else {
                answersPopularity.put(answerText, 1);// лучше сразу ставить на 1, а потом ее где-то в конце вычитать
            }
        }
        return answersPopularity;
    }

    private List<String> determineMostPopularAnswers(Map<String, Integer> answersPopularity) {
        int maximumPoints = 0;
        for (String answer: answersPopularity.keySet()) {
            if (answersPopularity.get(answer) > maximumPoints) {
                maximumPoints = answersPopularity.get(answer);

            }
        }

        List<String> mostPopularAnswers = new ArrayList<>();
        for (String answer: answersPopularity.keySet()) {
            if (answersPopularity.get(answer) == maximumPoints) {
                mostPopularAnswers.add(answer);
            }
        }
        return mostPopularAnswers;
    }


    private Map<Long, Integer> determinePointsOfEachTeam (List<Answer> answers, Map<String, Integer> answersPopularity) {
        Map<Long, Integer> teamIdPoints = new HashMap<>();

        for (Answer answer: answers) {
            String answerText = answer.getAnswer();
            long teamId = answer.getTeamId();
            int points = answersPopularity.get(answerText) - 1;
            teamIdPoints.put(teamId, points);
        }
        return teamIdPoints;
    }

}
