package ru.otus.processor.homework;

import java.time.LocalDateTime;

public class DefaultDateTimeProvider implements DateTimeProvider {

    @Override
    public LocalDateTime getDate() {
        return LocalDateTime.now();
    }
}
