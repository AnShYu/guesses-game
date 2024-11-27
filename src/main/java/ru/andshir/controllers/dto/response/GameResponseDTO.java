package ru.andshir.controllers.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GameResponseDTO {

    private long id;
    private int numberOfRounds;
    private LocalDateTime gameDate; // LocalDateTime не содержит информацию о TimeZone. Это нужно настраивать дополнительно
    List<RoundResponseDTO> questionsInRounds = new ArrayList<>();

}
