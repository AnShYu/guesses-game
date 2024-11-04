package ru.andshir.service.game.readiness.checker;

import org.junit.jupiter.api.Test;
import ru.andshir.model.Game;
import ru.andshir.model.Round;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SequentialRoundNumbersCheckerTest {

    @Test
    void correctSequenceTest() {
        Game game = makeGame(1,2,3);
        GameChecker gameChecker = new SequentialRoundNumbersChecker();
        assertTrue(gameChecker.check(game));
    }

    @Test
    void wrongSequenceTest() {
        Game game = makeGame(1,5,3);
        GameChecker gameChecker = new SequentialRoundNumbersChecker();
        assertFalse(gameChecker.check(game));
    }

    @Test
    void sequenceStartsWithZeroTest() {
        Game game = makeGame(1,5,3);
        GameChecker gameChecker = new SequentialRoundNumbersChecker();
        assertFalse(gameChecker.check(game));
    }

    @Test
    void oneRoundTest() {
        Game game = makeGame(1);
        GameChecker gameChecker = new SequentialRoundNumbersChecker();
        assertTrue(gameChecker.check(game));
    }






    private Game makeGame(int... roundNumber) {
        List<Round> gameRounds = new ArrayList<>();
        for (int i = 0; i < roundNumber.length; i++) {
            gameRounds.add(makeRound(roundNumber[i]));
        }

        Game game = new Game();
        game.setRoundsWithQuestions(gameRounds);

        return game;
    }

    private Round makeRound(int roundNumber) {
        Round round = new Round();
        round.setRoundNumber(roundNumber);
        return round;
    }

}