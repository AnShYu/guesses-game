package ru.andshir.service.game.readiness.checker;

import org.springframework.stereotype.Component;
import ru.andshir.model.Game;
import ru.andshir.model.Round;

import java.util.List;

@Component
public class NullQuestionsChecker implements GameChecker {

    @Override
    public boolean check(Game game) {
        boolean noNullQuestions = true;
        List<Round> gameRounds = game.getRoundsWithQuestions();

        for (Round round: gameRounds) {
            if (round.getQuestion() == null) {
                return false;
            }
        }

        return noNullQuestions;
    }
}
