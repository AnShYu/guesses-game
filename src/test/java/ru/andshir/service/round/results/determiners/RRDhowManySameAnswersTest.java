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
        RoundResultsWrapper roundResultsWrapper = makeRRDhowManySameAnswers(teamIds, answerTexts).determineRoundResults(GAME_ID, ROUND_NUMBER);

        Map<Long, Integer> expectedPointsByTeamIds = makePointsByTeamId(teamIds, new int[]{0,0,0});
        String[] expectedMostPopularAnswerTexts = {"Answer 1", "Answer 2", "Answer 3"};

        assertTrue(checkIfMostPopularAnswersListIsCorrect(expectedMostPopularAnswerTexts, roundResultsWrapper));
        assertTrue(checkIfTeamPointsMapIsCorrect(expectedPointsByTeamIds, roundResultsWrapper));
    }

    @Test
    void oneMostPopularAnswerTest() {
        long[] teamIds = {1,2,3};
        String[] answerTexts = {"Answer 1", "Answer 1", "Answer 3"};
        RoundResultsWrapper roundResultsWrapper = makeRRDhowManySameAnswers(teamIds, answerTexts).determineRoundResults(GAME_ID, ROUND_NUMBER);

        Map<Long, Integer> expectedPointsByTeamIds = makePointsByTeamId(teamIds, new int[]{1,1,0});
        String[] expectedMostPopularAnswerTexts = {"Answer 1"};

        assertTrue(checkIfMostPopularAnswersListIsCorrect(expectedMostPopularAnswerTexts, roundResultsWrapper));
        assertTrue(checkIfTeamPointsMapIsCorrect(expectedPointsByTeamIds, roundResultsWrapper));
    }

    @Test
    void twoMostPopularAnswerTest() {
        long[] teamIds = {1,2,3,4,5};
        String[] answerTexts = {"Answer 1", "Answer 1", "Answer 2", "Answer 2", "Ответ 3"};
        RoundResultsWrapper roundResultsWrapper = makeRRDhowManySameAnswers(teamIds, answerTexts).determineRoundResults(GAME_ID, ROUND_NUMBER);

        Map<Long, Integer> expectedPointsByTeamIds = makePointsByTeamId(teamIds, new int[]{1,1,1,1,0});
        String[] expectedMostPopularAnswerTexts = {"Answer 1", "Answer 2"};

        assertTrue(checkIfMostPopularAnswersListIsCorrect(expectedMostPopularAnswerTexts, roundResultsWrapper));
        assertTrue(checkIfTeamPointsMapIsCorrect(expectedPointsByTeamIds, roundResultsWrapper));
    }

    @Test
    void allSameAnswersTest() {
        long[] teamIds = {1,2,3};
        String[] answerTexts = {"Answer 1", "Answer 1", "Answer 1"};
        RoundResultsWrapper roundResultsWrapper = makeRRDhowManySameAnswers(teamIds, answerTexts).determineRoundResults(GAME_ID, ROUND_NUMBER);

        Map<Long, Integer> expectedPointsByTeamIds = makePointsByTeamId(teamIds, new int[]{2,2,2});
        String[] expectedMostPopularAnswerTexts = {"Answer 1"};

        assertTrue(checkIfMostPopularAnswersListIsCorrect(expectedMostPopularAnswerTexts, roundResultsWrapper));
        assertTrue(checkIfTeamPointsMapIsCorrect(expectedPointsByTeamIds, roundResultsWrapper));
    }

    @Test
    void oneTeamOneAnswerTest() {
        long[] teamIds = {1};
        String[] answerTexts = {"Answer 1"};
        RoundResultsWrapper roundResultsWrapper = makeRRDhowManySameAnswers(teamIds, answerTexts).determineRoundResults(GAME_ID, ROUND_NUMBER);

        Map<Long, Integer> expectedPointsByTeamIds = makePointsByTeamId(teamIds, new int[]{0});
        String[] expectedMostPopularAnswerTexts = {"Answer 1"};

        assertTrue(checkIfMostPopularAnswersListIsCorrect(expectedMostPopularAnswerTexts, roundResultsWrapper));
        assertTrue(checkIfTeamPointsMapIsCorrect(expectedPointsByTeamIds, roundResultsWrapper));
    }






    private Map<Long, Integer> makePointsByTeamId(long[] teamIds, int[] points) {
        Map<Long, Integer> pointsByTeamId = new HashMap<>();
        for (int i = 0; i< teamIds.length; i++) {
            pointsByTeamId.put(teamIds[i], points[i]);
        }
        return pointsByTeamId;
    }

    private RRDhowManySameAnswers makeRRDhowManySameAnswers(long[] teamIds, String[] answerTexts) {
        List<Answer> answers = makeListOfAnswers(teamIds, answerTexts);
        AnswersRepository mockAnswersRepository = Mockito.mock(AnswersRepository.class);
        Mockito.when(mockAnswersRepository.findByGameIdAndRoundNumber(Mockito.anyLong(), Mockito.anyInt())).thenReturn(answers);
        return new RRDhowManySameAnswers(mockAnswersRepository);
    }

    private boolean checkIfMostPopularAnswersListIsCorrect(String[] expectedMostPopularAnswerTexts, RoundResultsWrapper roundResultsWrapper) {
        List<String> actualMostPopularAnswers = roundResultsWrapper.getMostPopularAnswers();

        if (expectedMostPopularAnswerTexts.length != actualMostPopularAnswers.size()) {
            return false;
        } else {
            for (int i = 0; i<expectedMostPopularAnswerTexts.length; i++) {
                if (!actualMostPopularAnswers.contains(expectedMostPopularAnswerTexts[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIfTeamPointsMapIsCorrect(Map<Long, Integer> expectedPointsByTeamId, RoundResultsWrapper roundResultsWrapper) {
        Map<Long, Integer> actualPointsByTeamId = roundResultsWrapper.getPointsByTeamId();
        if (expectedPointsByTeamId.size() != actualPointsByTeamId.size()) {
            return false;
        } else {
            for (Long teamId: expectedPointsByTeamId.keySet()) {
                if (!actualPointsByTeamId.containsKey(teamId)) {
                    return false;
                } else if (!expectedPointsByTeamId.get(teamId).equals(actualPointsByTeamId.get(teamId))) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Answer> makeListOfAnswers(long[] teamIds, String[] answerTexts) {
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < teamIds.length; i++) {
            answers.add(makeAnswer(teamIds[i], answerTexts[i]));
        }
        return answers;
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