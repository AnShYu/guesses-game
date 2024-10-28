package ru.andshir.exceptions;

public class TeamNotAdmittedException extends RuntimeException {

    public TeamNotAdmittedException(String message) {
        super(message);
    }
}
