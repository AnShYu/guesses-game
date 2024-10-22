package ru.andshir.service;

import org.springframework.stereotype.Component;
import ru.andshir.model.Game;
import ru.andshir.model.Question;
import ru.andshir.model.Round;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameReadinessChecker {

    public boolean checkGameReadiness(Game game, int numberOfQuestions) {
        boolean gameIsReady = true;

        List<Round> gameRounds = game.getRoundsWithQuestions();
        if (gameRounds.size() != numberOfQuestions) {
            gameIsReady = false;
        } else {
            List<Long> questionIdsInGame = new ArrayList<>();
            for (Round round: gameRounds) {
                if (questionIdsInGame.contains(round.getQuestion().getId())) {
                    gameIsReady = false;
                } else {
                    questionIdsInGame.add(round.getQuestion().getId());
                }
            }
        }
        return gameIsReady;
    }

}
