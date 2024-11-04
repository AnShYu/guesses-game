package ru.andshir.exceptions;


public class RoundResultsNotReadyException extends RuntimeException {

    int numberOfMissingAnswers;

    public RoundResultsNotReadyException(String message, int numberOfMissingAnswers) {
        super(message);
        this.numberOfMissingAnswers = numberOfMissingAnswers;
    }

    public int getNumberOfMissingAnswers() {
        return numberOfMissingAnswers;
    }
}
