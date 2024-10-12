package ru.andshir.controllers.dto.response;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class RoundResponseDTO {

    private long gameId;
    private int roundNumber;
    private long questionId;

}
