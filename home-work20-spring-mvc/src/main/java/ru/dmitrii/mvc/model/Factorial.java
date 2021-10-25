package ru.dmitrii.mvc.model;

import java.time.LocalDateTime;

public class Factorial {
    private final long result;
    private final LocalDateTime dateTime;

    public Factorial(long result) {
        this.result = result;
        this.dateTime = LocalDateTime.now();
    }

    public long getResult() {
        return result;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
