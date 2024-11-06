package ru.andshir.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.stereotype.Component;

@Entity
@Data
@Table(name = "round_results")
public class RoundResult {

    @EmbeddedId
    private RoundResultId roundResultId;
    private int points;

}
