package ru.andshir.service.round_results_determiners;

import org.springframework.stereotype.Component;
import ru.andshir.controllers.dto.response.RoundResultsResponseDTO;

@Component("RRDhowManySameAnswers")
public class RRDhowManySameAnswers implements RoundResultsDeterminer {

    @Override
    public RoundResultsResponseDTO determineRoundResults(long gameId, int currentRoundNumber) {
        return null;//TODO
    }
}
