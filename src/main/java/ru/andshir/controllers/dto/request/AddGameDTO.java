package ru.andshir.controllers.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddGameDTO {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime gameDate;
    @Min(value = 1, message = "Please set number of rounds in the game (minimum 1)")
    private int numberOfRounds;
}
