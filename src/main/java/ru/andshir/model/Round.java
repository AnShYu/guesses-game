package ru.andshir.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "rounds")
@IdClass(RoundId.class)
public class Round {

    @Id
//    @ManyToOne
//    @JoinColumn(name = "game_id")
    private long gameId;
    @Id
    private int roundNumber;
    private long questionId;
}
