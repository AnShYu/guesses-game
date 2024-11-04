package ru.andshir.service.game.readiness.checker;

import org.junit.jupiter.api.Test;
import ru.andshir.model.Game;
import ru.andshir.model.Round;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumberOfRoundsCheckerTest {

    @Test
    void correctNumberOfRoundsTest() {
        Game game = makeGame(3,3);
        GameChecker gameChecker = new NumberOfRoundsChecker();
        assertTrue(gameChecker.check(game));
    }

    @Test
    void tooManyRoundsTest() {
        Game game = makeGame(3,2);
        GameChecker gameChecker = new NumberOfRoundsChecker();
        assertFalse(gameChecker.check(game));
    }

    @Test
    void notEnoughRoundsTest() {
        Game game = makeGame(2,3);
        GameChecker gameChecker = new NumberOfRoundsChecker();
        assertFalse(gameChecker.check(game));
    }






    private Game makeGame(int actualNumberOfRounds, int expectedNumberOfRounds) {
        List<Round> gameRounds = new ArrayList<>();
        for (int i = 0; i < actualNumberOfRounds; i++) {
            gameRounds.add(new Round());
        }

        Game game = new Game();
        game.setRoundsWithQuestions(gameRounds);
        game.setNumberOfRounds(expectedNumberOfRounds);

        return game;
    }
}