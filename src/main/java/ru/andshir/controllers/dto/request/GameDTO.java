package ru.andshir.controllers.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameDTO {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime gameDate;
}
