package ru.pankov.store.err;

public class InsufficientNumberOfItemsException extends RuntimeException {

    public InsufficientNumberOfItemsException(String message) {
        super(message);
    }
}
