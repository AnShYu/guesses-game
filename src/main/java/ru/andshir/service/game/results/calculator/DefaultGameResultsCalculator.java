package ru.andshir.service.game.results.calculator;

import org.springframework.stereotype.Component;
import ru.andshir.controllers.dto.response.GameResultsResponseDTO;
import ru.andshir.model.RoundResult;
import ru.andshir.model.RoundResultId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DefaultGameResultsCalculator implements GameResultsCalculator {

    @Override
    public Map<Long, Integer> calculateGameResults(List<RoundResult> allGameRoundResults) {
        Map<Long, Integer> totalTeamPointsByTeamId = new HashMap<>();
        for (RoundResult roundResult: allGameRoundResults) {
            long teamId = roundResult.getRoundResultId().getTeamId();
            int pointsToAdd = roundResult.getPoints();

            if(totalTeamPointsByTeamId.containsKey(teamId)) {
                int currentPoints = totalTeamPointsByTeamId.get(teamId);
                totalTeamPointsByTeamId.put(teamId, currentPoints + pointsToAdd);
            } else {
                totalTeamPointsByTeamId.put(teamId, pointsToAdd);
            }
        }
        return totalTeamPointsByTeamId;
    }
}
