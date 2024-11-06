package ru.andshir.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoundResultId implements Serializable {

    private long gameId;
    private int roundNumber;
    private long teamId;


}
