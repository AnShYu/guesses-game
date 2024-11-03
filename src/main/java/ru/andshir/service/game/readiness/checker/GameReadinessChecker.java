package ru.andshir.service.game.readiness.checker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.andshir.model.Game;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GameReadinessChecker {

    private final List<GameChecker> gameCheckers;

    public boolean checkGameReadiness(Game game) {
        for (GameChecker gameChecker: gameCheckers) {
            if (!gameChecker.check(game)) {
                return false;
            }
        }
        return true;
    }

}
