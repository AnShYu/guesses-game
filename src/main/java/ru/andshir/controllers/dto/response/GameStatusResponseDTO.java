package ru.andshir.controllers.dto.response;

import lombok.Data;
import ru.andshir.model.Question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GameStatusResponseDTO {

    private long id;
    private LocalDateTime gameDate;
    List<Question> questions = new ArrayList<>();
    int numberOfRounds;

}
