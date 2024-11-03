package ru.andshir.mappers;

import org.springframework.stereotype.Component;
import ru.andshir.controllers.dto.response.RoundResultsResponseDTO;
import ru.andshir.service.round.results.determiners.RoundResultsWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoundResultsMapper {

    public RoundResultsResponseDTO wrapperToDTO(RoundResultsWrapper roundResultsWrapper, Map<String, Long> teamTeamId) {
        Map<Long, Integer> teamIdPoints = roundResultsWrapper.getTeamIdPoints();
        List<String> mostPopularAnswers = roundResultsWrapper.getMostPopularAnswers();

        Map<String, Integer> teamPoints = new HashMap<>();
        for (String teamName: teamTeamId.keySet()) {
            int points = teamIdPoints.get(teamTeamId.get(teamName));
            teamPoints.put(teamName, points);
        }

        RoundResultsResponseDTO roundResultsResponseDTO = new RoundResultsResponseDTO();
        roundResultsResponseDTO.setTeamPoints(teamPoints);
        roundResultsResponseDTO.setMostPopularAnswers(mostPopularAnswers);

        return roundResultsResponseDTO;
    }

}
