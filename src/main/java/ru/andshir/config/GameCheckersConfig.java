package ru.andshir.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.andshir.service.game.readiness.checker.*;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GameCheckersConfig {

    private final NoDuplicateQuestionsChecker noDuplicateQuestionsChecker;
    private final NullQuestionsChecker nullQuestionsChecker;
    private final NumberOfRoundsChecker numberOfRoundsChecker;
    private final SequentialRoundNumbersChecker sequentialRoundNumbersChecker;

    @Bean
    public CheckersAggregator getCheckersAggregator() {
        CheckersAggregator checkersAggregator = new CheckersAggregator();
        List<GameChecker> checkers = checkersAggregator.getCheckers();
        checkers.add(noDuplicateQuestionsChecker);
        checkers.add(nullQuestionsChecker);
        checkers.add(numberOfRoundsChecker);
        checkers.add(sequentialRoundNumbersChecker);
        return checkersAggregator;
    }
}
