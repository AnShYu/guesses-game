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
    void twoDuplicatingQuestionsTest() {
        List<Round> gameRounds = new ArrayList<>();

        Round round1 = makeRound(1l);
        Round round2 = makeRound(1l);
        gameRounds.add(round1);
        gameRounds.add(round2);

        Game game = new Game();
        game.setRoundsWithQuestions(gameRounds);

        GameChecker gameChecker = new NoDuplicateQuestionsChecker();
        assertFalse(gameChecker.check(game));
    }

    @Test
    void threeNoDuplicatingQuestionsTest() {
        List<Round> gameRounds = new ArrayList<>();

        Round round1 = makeRound(1l);
        Round round2 = makeRound(2l);
        Round round3 = makeRound(3l);
        gameRounds.add(round1);
        gameRounds.add(round2);
        gameRounds.add(round3);

        Game game = new Game();
        game.setRoundsWithQuestions(gameRounds);

        GameChecker gameChecker = new NoDuplicateQuestionsChecker();
        assertTrue(gameChecker.check(game));
    }

    private Round makeRound(long questionId) {
        Round round = new Round();
        Question question = new Question();
        question.setId(questionId);
        round.setQuestion(question);
        return round;
    }

}

