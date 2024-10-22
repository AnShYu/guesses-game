package ru.andshir.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "answers")
@IdClass(AnswerId.class)
public class Answer {

    @Id
    private long teamId;
    @Id
    private long gameId;
    @Id
    private int roundNumber;
    private String answer;

}
