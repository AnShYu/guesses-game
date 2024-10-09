package ru.andshir.controllers.dto.response;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import ru.andshir.model.Question;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GameResponseDTO {

    private long id;
    private LocalDateTime gameDate; // LocalDateTime не содержит информацию о TimeZone. Это нужно настраивать дополнительно
    List<Question> gameQuestions;

}
