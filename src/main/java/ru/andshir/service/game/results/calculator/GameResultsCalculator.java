package ru.andshir.service.game.results.calculator;

import ru.andshir.model.RoundResult;

import java.util.List;
import java.util.Map;

public interface GameResultsCalculator {

    Map<Long, Integer> calculateGameResults(List<RoundResult> allGameRoundResults);

}
