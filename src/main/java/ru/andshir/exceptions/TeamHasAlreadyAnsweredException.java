package ru.andshir.exceptions;

public class TeamHasAlreadyAnsweredException extends RuntimeException {

    public TeamHasAlreadyAnsweredException(String message) {
        super(message);
    }
}
