package ru.andshir.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.andshir.service.game.results.calculator.DefaultGameResultsCalculator;
import ru.andshir.service.game.results.calculator.GameResultsCalculator;

@Configuration
@RequiredArgsConstructor
public class GameResultsCalculationConfig {

    @Bean
    public GameResultsCalculator resultsCalculator() {
        GameResultsCalculator gameResultsCalculator = new DefaultGameResultsCalculator();
        return gameResultsCalculator;
    }

}
