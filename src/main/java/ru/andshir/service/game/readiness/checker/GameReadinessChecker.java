package ru.andshir.service.game.readiness.checker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.andshir.model.Game;

@Component
@RequiredArgsConstructor
public class GameReadinessChecker {

    private final CheckersAggregator checkersAggregator;

    public boolean checkGameReadiness(Game game) {
        for (GameChecker gameChecker: checkersAggregator.getCheckers()) {
            if (!gameChecker.check(game)) {
                return false;
            }
        }
        return true;
    }

}
