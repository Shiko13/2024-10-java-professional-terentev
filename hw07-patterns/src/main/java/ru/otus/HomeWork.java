package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.ProcessorConcatFields;
import ru.otus.processor.ProcessorUpperField10;
import ru.otus.processor.homework.ProcessorSwapFields;
import ru.otus.processor.homework.ProcessorThrowExceptionInEvenSecond;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

public class HomeWork {

    public static void main(String[] args) {
        var originalMessage = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(null)
                .build();

        var processorSwap = new ProcessorSwapFields();
        var processorThrowExceptionInEvenSecond = new ProcessorThrowExceptionInEvenSecond(LocalDateTime::now);
        var processorLogger = new LoggerProcessor(new ProcessorConcatFields());
        var processorUpperField10 = new ProcessorUpperField10();

        var historyListener = new HistoryListener();

        var complexProcessor = new ComplexProcessor(
                List.of(processorSwap, processorThrowExceptionInEvenSecond, processorLogger, processorUpperField10),
                errorHandler()
        );

        complexProcessor.addListener(historyListener);

        var processedMessage = complexProcessor.handle(originalMessage);

        System.out.println("Processed message: " + processedMessage);

        historyListener.onUpdated(processedMessage);
        System.out.println("Message from history: " + historyListener.findMessageById(1L).orElse(null));
    }

    private static Consumer<Exception> errorHandler() {
        return ex -> System.err.println("Error during processing: " + ex.getMessage());
    }
}
