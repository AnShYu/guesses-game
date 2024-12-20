package ru.andshir.mappers;

import org.springframework.stereotype.Component;
import ru.andshir.controllers.dto.response.RoundResultsResponseDTO;
import ru.andshir.model.RoundResult;
import ru.andshir.repository.RoundResultsRepository;
import ru.andshir.service.round.results.determiners.RoundResultsWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoundResultsMapper {

    public RoundResultsResponseDTO wrapperToDTO(RoundResultsWrapper roundResultsWrapper, Map<Long, String> teamNameByTeamId) {
        Map<Long, Integer> pointsByTeamId = roundResultsWrapper.getPointsByTeamId();
        List<String> mostPopularAnswers = roundResultsWrapper.getMostPopularAnswers();

        Map<String, Integer> teamPoints = new HashMap<>();
        for (Long teamId: teamNameByTeamId.keySet()) {
            int points = pointsByTeamId.get(teamId);
            teamPoints.put(teamNameByTeamId.get(teamId), points);
        }

        RoundResultsResponseDTO roundResultsResponseDTO = new RoundResultsResponseDTO();
        roundResultsResponseDTO.setTeamPoints(teamPoints);
        roundResultsResponseDTO.setMostPopularAnswers(mostPopularAnswers);

        return roundResultsResponseDTO;
    }

//    public RoundResultsResponseDTO listOfRoundResultsToDTO(List<RoundResult> roundResults) {
//
//
//TODO мы нигде не сохраняем самые популярные ответы
//
//
//
//
//
//        List<String> mostPopularAnswers = new ArrayList<>();
//        Map<String, Integer> teamPoints = new HashMap<>();
//    }



}
