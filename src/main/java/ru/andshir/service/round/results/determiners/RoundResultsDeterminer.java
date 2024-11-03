package ru.andshir.service.round.results.determiners;

public interface RoundResultsDeterminer {

    RoundResultsWrapper determineRoundResults(long gameId, int currentRoundNumber);

}
