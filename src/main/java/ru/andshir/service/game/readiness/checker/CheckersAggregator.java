package ru.andshir.service.game.readiness.checker;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class CheckersAggregator {

    private List<GameChecker> checkers = new ArrayList<>();

}
