package ru.andshir.service;

import ru.andshir.controllers.dto.response.RoundResultsResponseDTO;

//TODO какие аннотации нужны интерфейсу
public interface RoundResultsDeterminer {

    RoundResultsResponseDTO determineRoundResults(long gameId, int currentRoundNumber);

}
