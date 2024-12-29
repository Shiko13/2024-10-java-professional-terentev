package ru.otus.homework;

import ru.otus.processor.homework.DateTimeProvider;

import java.time.LocalDateTime;

public class StubDateTimeProvider implements DateTimeProvider {

    private final LocalDateTime dateTime;

    public StubDateTimeProvider(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public LocalDateTime getDate() {
        return dateTime;
    }
}
