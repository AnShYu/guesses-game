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
    @ManyToMany
    @JoinTable(
            name = "games_questions",
            joinColumns = { @JoinColumn(name = "game_id") },
            inverseJoinColumns = { @JoinColumn(name = "question_id") }
    )
    List<Question> gameQuestions;
}
