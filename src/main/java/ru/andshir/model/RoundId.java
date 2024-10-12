package ru.andshir.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoundId implements Serializable {

    private long gameId;
    private int roundNumber;

}
