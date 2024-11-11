package ru.andshir.service.round.results.determiners;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class RoundResultsWrapper {

    List<String> mostPopularAnswers = new ArrayList<>();
    Map<Long, Integer> pointsByTeamId = new HashMap<>();

}
