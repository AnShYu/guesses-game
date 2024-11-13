package ru.andshir.service;


import org.aspectj.weaver.Checker;
import org.junit.jupiter.api.Test;
import ru.andshir.model.Game;
import ru.andshir.model.Question;
import ru.andshir.model.Round;
import ru.andshir.service.game.readiness.checker.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameReadinessCheckerTest {

    @Test
    void gameIsReadyTest() {
        long[] questionIds = {1,2,3};
        int[] roundNumbers = {1,2,3};
        Game game = makeGame(false, questionIds, roundNumbers, 3);
        GameReadinessChecker gameReadinessChecker = makeGameReadinessChecker();
        assertTrue(gameReadinessChecker.checkGameReadiness(game));
    }






    private Game makeGame(boolean withNullQuestion, long[] questionIds, int[] roundNumbers, int numberOfRounds) {
        List<Round> gameRounds = new ArrayList<>();
        for (int i = 0; i < roundNumbers.length; i++) {
            gameRounds.add(makeRound(questionIds[i], roundNumbers[i]));
        }
        if (withNullQuestion) gameRounds.add(makeRoundWithNullQuestion(gameRounds.size()+1));

        Game game = new Game();
        game.setRoundsWithQuestions(gameRounds);
        game.setNumberOfRounds(numberOfRounds);

        return game;
    }

    private Round makeRound(long questionId, int roundNumber) {
        Round round = new Round();
        round.setRoundNumber(roundNumber);
        Question question = new Question();
        question.setId(questionId);
        round.setQuestion(question);
        return round;
    }

    private Round makeRoundWithNullQuestion(int roundNumber) {
        Round round = new Round();
        round.setRoundNumber(roundNumber);
        round.setQuestion(null);
        return round;
    }

    private GameReadinessChecker makeGameReadinessChecker() {
        List<GameChecker> checkers = new ArrayList<>();
        checkers.add(new NullQuestionsChecker());
        checkers.add(new NoDuplicateQuestionsChecker());
        checkers.add(new NumberOfRoundsChecker());
        checkers.add(new SequentialRoundNumbersChecker());
        return new GameReadinessChecker(checkers);
    }

}