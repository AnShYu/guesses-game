package ru.andshir.service.round.results.determiners;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.andshir.model.Answer;
import ru.andshir.repository.AnswersRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RRDhowManySameAnswersTest {

    private final static long GAME_ID = 1;
    private final static int ROUND_NUMBER = 1;

    @Test
    void allGaveDifferentAnswersTest() {
        long[] teamIds = {1,2,3};
        String[] answerTexts = {"Answer 1", "Answer 2", "Answer 3"};
        RRDhowManySameAnswers rRDhowManySameAnswers = makeRRDhowManySameAnswers(teamIds, answerTexts);

        String[] mostPopularAnswerTexts = {"Answer 1", "Answer 2", "Answer 3"};
        int[] points = {0,0,0};
        RoundResultsWrapper roundResultsWrapper = makeRoundResultsWrapper(mostPopularAnswerTexts, teamIds, points);

        assertEquals(roundResultsWrapper, rRDhowManySameAnswers.determineRoundResults(GAME_ID, ROUND_NUMBER));
    }

    @Test
    void oneMostPopularAnswerTest() {
        long[] teamIds = {1,2,3};
        String[] answerTexts = {"Answer 1", "Answer 1", "Answer 3"};
        RRDhowManySameAnswers rRDhowManySameAnswers = makeRRDhowManySameAnswers(teamIds, answerTexts);

        String[] mostPopularAnswerTexts = {"Answer 1"};
        int[] points = {1,1,0};
        RoundResultsWrapper roundResultsWrapper = makeRoundResultsWrapper(mostPopularAnswerTexts, teamIds, points);

        assertEquals(roundResultsWrapper, rRDhowManySameAnswers.determineRoundResults(GAME_ID, ROUND_NUMBER));
    }

    @Test
    void twoMostPopularAnswersTest() {
        long[] teamIds = {1,2,3,4,5};
        String[] answerTexts = {"Answer 1", "Answer 1", "Answer 2", "Answer 2", "Ответ 3"};
        RRDhowManySameAnswers rRDhowManySameAnswers = makeRRDhowManySameAnswers(teamIds, answerTexts);

        String[] mostPopularAnswerTexts = {"Answer 1", "Answer 2"};
        int[] points = {1,1,1,1,0};
        RoundResultsWrapper roundResultsWrapper = makeRoundResultsWrapper(mostPopularAnswerTexts, teamIds, points);

        assertEquals(roundResultsWrapper, rRDhowManySameAnswers.determineRoundResults(GAME_ID, ROUND_NUMBER));
    }

    @Test
    void allSameAnswersTest() {
        long[] teamIds = {1,2,3};
        String[] answerTexts = {"Answer 1", "Answer 1", "Answer 1"};
        RRDhowManySameAnswers rRDhowManySameAnswers = makeRRDhowManySameAnswers(teamIds, answerTexts);

        String[] mostPopularAnswerTexts = {"Answer 1"};
        int[] points = {2,2,2};
        RoundResultsWrapper roundResultsWrapper = makeRoundResultsWrapper(mostPopularAnswerTexts, teamIds, points);

        assertEquals(roundResultsWrapper, rRDhowManySameAnswers.determineRoundResults(GAME_ID, ROUND_NUMBER));
    }

    private RRDhowManySameAnswers makeRRDhowManySameAnswers(long[] teamIds, String[] answerTexts) {
        List<Answer> answers = makeListOfAnswers(teamIds, answerTexts);
        AnswersRepository mockAnswersRepository = Mockito.mock(AnswersRepository.class);
        Mockito.when(mockAnswersRepository.findByGameIdAndRoundNumber(Mockito.anyLong(), Mockito.anyInt())).thenReturn(answers);
        return new RRDhowManySameAnswers(mockAnswersRepository);
    }

    private RoundResultsWrapper makeRoundResultsWrapper(String[] mostPopularAnswerTexts, long[] teamIds, int[] points) {
        List<String> mostPopularAnswers = makeListOfMostPopularAnswers(mostPopularAnswerTexts);
        Map<Long, Integer> teamIdPoints = makeTeamIdPointsMap(teamIds, points);
        RoundResultsWrapper roundResultsWrapper = new RoundResultsWrapper();
        roundResultsWrapper.setMostPopularAnswers(mostPopularAnswers);
        roundResultsWrapper.setTeamIdPoints(teamIdPoints);
        return roundResultsWrapper;
    }

    private List<Answer> makeListOfAnswers(long[] teamIds, String[] answerTexts) {
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < teamIds.length; i++) {
            answers.add(makeAnswer(teamIds[i], answerTexts[i]));
        }
        return answers;
    }

    private List<String> makeListOfMostPopularAnswers(String[] answerTexts) {
        //TODO в тестируемом методе список самых популярных ответов собирается из мапы, в которой не гарантирован порядок
        Map<String, Integer> technicalMap = new HashMap<>();
        for (String answerText : answerTexts) {
            technicalMap.put(answerText, 0);
        }
        List<String> mostPopularAnswers = new ArrayList<>();
        mostPopularAnswers.addAll(technicalMap.keySet());
        return mostPopularAnswers;
    }

    private Map<Long, Integer> makeTeamIdPointsMap(long[] teamIds, int[] points) {
        Map<Long, Integer> teamIdPoints = new HashMap<>();
        for (int i = 0; i < teamIds.length; i++) {
            teamIdPoints.put(teamIds[i], points[i]);
        }
        return teamIdPoints;
    }

    private Answer makeAnswer(long teamId, String answerText) {
        Answer answer = new Answer();
        answer.setTeamId(teamId);
        answer.setGameId(GAME_ID);
        answer.setRoundNumber(ROUND_NUMBER);
        answer.setAnswer(answerText);
        return answer;
    }
}