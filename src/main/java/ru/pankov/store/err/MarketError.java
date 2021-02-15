package ru.pankov.store.err;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MarketError {
    private int status;
    private String message;
    private List<String> messages;
    private Date timestamp;

    public MarketError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }

    public MarketError(int status, List<String> messages) {
        this.status = status;
        this.messages = messages;
        this.timestamp = new Date();
    }
}
