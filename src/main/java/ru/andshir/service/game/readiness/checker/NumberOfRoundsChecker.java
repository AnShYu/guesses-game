package ru.andshir.service.game.readiness.checker;

import org.springframework.stereotype.Component;
import ru.andshir.model.Game;

@Component
public class NumberOfRoundsChecker implements GameChecker {

    @Override
    public boolean check(Game game) {
        return game.getRoundsWithQuestions().size() == game.getNumberOfRounds();
    }
}
