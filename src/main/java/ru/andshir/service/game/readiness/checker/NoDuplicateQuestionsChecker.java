package ru.andshir.service.game.readiness.checker;

import org.springframework.stereotype.Component;
import ru.andshir.model.Game;
import ru.andshir.model.Round;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class NoDuplicateQuestionsChecker implements GameChecker {

    @Override
    public boolean check(Game game) {
        boolean noDuplicates = true;
        List<Round> gameRounds = game.getRoundsWithQuestions();
        Set<Long> questionIdsInGame = new HashSet<>();

        for (Round round: gameRounds) {
            if (questionIdsInGame.contains(round.getQuestion().getId())) {
                noDuplicates = false;
            } else {
                questionIdsInGame.add(round.getQuestion().getId());
            }
        }

        return noDuplicates;
    }
}
