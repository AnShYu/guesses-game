package ru.andshir.controllers.dto.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GameResultsResponseDTO {

    private Map<String, Integer> teamPointsByTeamName = new HashMap<>();

}
