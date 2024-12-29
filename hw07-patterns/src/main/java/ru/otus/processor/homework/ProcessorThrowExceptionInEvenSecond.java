package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorThrowExceptionInEvenSecond implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorThrowExceptionInEvenSecond(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        var second = dateTimeProvider.getDate().getSecond();
        if (second % 2 == 0) {
            throw new IllegalStateException("Exception in even second: " + second);
        }
        return message;
    }
}
