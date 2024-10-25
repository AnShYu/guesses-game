package ru.andshir.controllers.dto.response;

import lombok.Data;

import java.util.HashMap;

@Data
public class RoundResultsResponseDTO {

    private String mostPopularAnswer;
    //TODO как хэшмап получать из базы и как она передается в json
    HashMap<String, Integer> teamPoints = new HashMap<>();

}
