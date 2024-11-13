package ru.andshir.service.game.readiness.checker;

import org.springframework.stereotype.Component;
import ru.andshir.model.Game;
import ru.andshir.model.Round;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SequentialRoundNumbersChecker implements GameChecker {

    @Override
    public boolean check(Game game) {
        Set<Integer> allActualRoundNumbers = getAllActualRoundNumbers(game);
        int intendedNumberOfRounds = game.getNumberOfRounds();

        if (intendedNumberOfRounds != allActualRoundNumbers.size()) {
            return false;
        } else {
            for (int i = 1; i<=intendedNumberOfRounds; i++) {
                if (!allActualRoundNumbers.contains(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private Set<Integer> getAllActualRoundNumbers(Game game) {
        return game.getRoundsWithQuestions().stream()
                .map(Round::getRoundNumber)
                .collect(Collectors.toSet());
    }
}
