package ru.andshir.service.game.readiness.checker;

import org.springframework.stereotype.Component;
import ru.andshir.model.Game;
import ru.andshir.model.Round;

import java.util.List;

@Component
public class SequentialRoundNumbersChecker implements GameChecker {

    @Override
    public boolean check(Game game) {
        boolean sequentialRoundNumbers = true;
        int counter = 1;
        List<Round> gameRounds = game.getRoundsWithQuestions();

        for (Round round: gameRounds) {
            if (round.getRoundNumber() == counter) {
                counter++;
            } else {
                return false;
            }
        }

        return sequentialRoundNumbers;
    }
}
