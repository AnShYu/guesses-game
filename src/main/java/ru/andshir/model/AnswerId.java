package ru.andshir.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerId implements Serializable {

    private long teamId;
    private long gameId;
    private int roundNumber;

}
