package ru.andshir.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime gameDate; // LocalDateTime не содержит информацию о TimeZone. Это нужно настраивать дополнительно
    @OneToMany(mappedBy = "gameId")
    List<Round> roundsWithQuestions;
}