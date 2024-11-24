package ru.andshir.mappers;

import org.springframework.stereotype.Component;
import ru.andshir.controllers.dto.response.GameResultsResponseDTO;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameResultsMapper {


    public GameResultsResponseDTO teamNameAndTeamIdAndPointsToGameResultsResponseDTO(Map<Long, Integer> totalTeamPointsByTeamId, Map<Long, String> teamNameByTeamId) {
        Map<String, Integer> pointsByTeamName = new HashMap<>();
        for (Long teamId: totalTeamPointsByTeamId.keySet()) {
            String teamName = teamNameByTeamId.get(teamId);
            Integer points = totalTeamPointsByTeamId.get(teamId);
            pointsByTeamName.put(teamName, points);
        }
        GameResultsResponseDTO gameResultsResponseDTO = new GameResultsResponseDTO();
        gameResultsResponseDTO.setTeamPointsByTeamName(pointsByTeamName);
        return gameResultsResponseDTO;
    }
}
