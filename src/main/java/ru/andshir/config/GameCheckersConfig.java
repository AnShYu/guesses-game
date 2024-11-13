package ru.andshir.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.andshir.service.game.readiness.checker.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GameCheckersConfig {


    private final NullQuestionsChecker nullQuestionsChecker;
    private final NoDuplicateQuestionsChecker noDuplicateQuestionsChecker;
    private final NumberOfRoundsChecker numberOfRoundsChecker;
    private final SequentialRoundNumbersChecker sequentialRoundNumbersChecker;

    @Bean
    public List<GameChecker> gameCheckers() {
        List<GameChecker> checkers = new ArrayList<>();
        checkers.add(nullQuestionsChecker);
        checkers.add(noDuplicateQuestionsChecker);
        checkers.add(numberOfRoundsChecker);
        checkers.add(sequentialRoundNumbersChecker);
        return checkers;
    }


}
