package ru.andshir.controllers.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class RoundResultsResponseDTO {

    private List<String> mostPopularAnswers = new ArrayList<>();
    private Map<String, Integer> teamPoints = new HashMap<>();

}
