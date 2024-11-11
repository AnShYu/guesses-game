package ru.andshir.service.game.readiness.checker;

import org.junit.jupiter.api.Test;
import ru.andshir.model.Game;
import ru.andshir.model.Question;
import ru.andshir.model.Round;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoDuplicateQuestionsCheckerTest {

    @Test
    void twoDuplicatingQuestionsOnlyTest() {
        Game game = makeGame(1, 1);
        GameChecker gameChecker = new NoDuplicateQuestionsChecker();
        assertFalse(gameChecker.check(game));
    }

    @Test
    void noDuplicatingQuestionsTest() {
        Game game = makeGame(1, 2, 3);
        GameChecker gameChecker = new NoDuplicateQuestionsChecker();
        assertTrue(gameChecker.check(game));
    }

    @Test
    void twoDuplicatingQuestionsAndOneOriginalTest() {
        Game game = makeGame(1, 2, 1);
        GameChecker gameChecker = new NoDuplicateQuestionsChecker();
        assertFalse(gameChecker.check(game));
    }

    @Test
    void oneQuestionTest() {
        Game game = makeGame(1);
        GameChecker gameChecker = new NoDuplicateQuestionsChecker();
        assertTrue(gameChecker.check(game));
    }





    private Game makeGame(long... questionId) {
        List<Round> gameRounds = new ArrayList<>();
        for (int i = 0; i < questionId.length; i++) {
            gameRounds.add(makeRound(questionId[i]));
        }

        Game game = new Game();
        game.setRoundsWithQuestions(gameRounds);

        return game;
    }

    private Round makeRound(long questionId) {
        Round round = new Round();
        Question question = new Question();
        question.setId(questionId);
        round.setQuestion(question);
        return round;
    }

}

