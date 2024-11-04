package ru.andshir.service.game.readiness.checker;

import org.junit.jupiter.api.Test;
import ru.andshir.model.Game;
import ru.andshir.model.Question;
import ru.andshir.model.Round;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NullQuestionsCheckerTest {

    @Test
    void oneNullQuestionTest() {
        Game game = makeGame(true);
        GameChecker gameChecker = new NullQuestionsChecker();
        assertFalse(gameChecker.check(game));
    }

    @Test
    void noNullQuestionTest() {
        Game game = makeGame(false);
        GameChecker gameChecker = new NullQuestionsChecker();
        assertTrue(gameChecker.check(game));
    }






    private Game makeGame(boolean withNullQuestion) {
        List<Round> gameRounds = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            gameRounds.add(makeRound(i));
        }
        if (withNullQuestion) gameRounds.add(makeRoundWithNullQuestion());

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

    private Round makeRoundWithNullQuestion() {
        Round round = new Round();
        round.setQuestion(null);
        return round;
    }
}