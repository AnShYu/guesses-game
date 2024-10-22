package ru.andshir.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String teamName;
    private long gameId;
}
