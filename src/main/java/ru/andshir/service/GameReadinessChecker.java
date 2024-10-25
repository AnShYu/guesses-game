package ru.andshir.service;

import org.springframework.stereotype.Component;
import ru.andshir.model.Game;
import ru.andshir.model.Round;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GameReadinessChecker {

    public boolean checkGameReadiness(Game game, int numberOfRounds) {
        boolean gameIsReady = true;

        List<Round> gameRounds = game.getRoundsWithQuestions();
        if (gameRounds.size() != numberOfRounds) {
            gameIsReady = false;
        } else if (!checkSequentialRoundNumbers(game)) {
            gameIsReady = false;
        } else if (!checkNoNullQuestions(game)) {
            gameIsReady = false;
        } else if (!checkNoDuplicateQuestions(game)) {
            gameIsReady = false;
        }
        return gameIsReady;
    }

    private boolean checkSequentialRoundNumbers(Game game) {
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

    private boolean checkNoNullQuestions(Game game) {
        boolean noNullQuestions = true;

        List<Round> gameRounds = game.getRoundsWithQuestions();

        for (Round round: gameRounds) {
            if (round.getQuestion() == null) {
                return false;
            }
        }

        return noNullQuestions;
    }

    private boolean checkNoDuplicateQuestions(Game game) {
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
