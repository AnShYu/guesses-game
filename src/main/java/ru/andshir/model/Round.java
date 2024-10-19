package ru.andshir.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "rounds")
@IdClass(RoundId.class)
public class Round {

    @Id
    private long gameId;
    @Id
    private int roundNumber;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
