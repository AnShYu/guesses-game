package ru.andshir.exceptions;

public class GameNotReadyException extends RuntimeException {

    public GameNotReadyException(String message) {
        super(message);
    }

}
