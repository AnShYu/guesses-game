package ru.andshir.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "current_rounds")
public class CurrentRound {

    @Id
    private long gameId;
    private int currentRoundNumber;
    private boolean resultsSaved;
}
