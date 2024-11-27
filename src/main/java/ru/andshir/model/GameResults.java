package ru.andshir.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Map;

//@Entity
@Table(name = "game_results")
@Data
public class GameResults {

    @Id
    private long gameId;
    private Map<Team, Integer> totalPointsByTeam;

}
