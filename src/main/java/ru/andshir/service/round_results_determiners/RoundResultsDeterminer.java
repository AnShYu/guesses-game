package ru.andshir.service.round_results_determiners;

import ru.andshir.controllers.dto.response.RoundResultsResponseDTO;
import ru.andshir.model.Answer;

import java.util.List;

//TODO какие аннотации нужны интерфейсу
public interface RoundResultsDeterminer {

    RoundResultsResponseDTO determineRoundResults(long gameId, int currentRoundNumber);

}
