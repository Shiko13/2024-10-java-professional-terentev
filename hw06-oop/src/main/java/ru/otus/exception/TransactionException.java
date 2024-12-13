package ru.otus.exception;

public class TransactionException extends RuntimeException {

    public TransactionException(String message) {
        super(message);
    }
}
