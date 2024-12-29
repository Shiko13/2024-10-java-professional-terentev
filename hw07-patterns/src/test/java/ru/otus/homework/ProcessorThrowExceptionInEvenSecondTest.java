package ru.otus.homework;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.ProcessorThrowExceptionInEvenSecond;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProcessorThrowExceptionInEvenSecondTest {

    @Test
    void shouldThrowExceptionInEvenSecond() {
        var dateTimeProvider = new StubDateTimeProvider(LocalDateTime.of(2024, 12, 29, 15, 30, 2));
        var processor = new ProcessorThrowExceptionInEvenSecond(dateTimeProvider);
        var message = new Message.Builder(1L).build();

        assertThatThrownBy(() -> processor.process(message))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Exception in even second: 2");
    }

    @Test
    void shouldNotThrowExceptionInOddSecond() {
        var dateTimeProvider = new StubDateTimeProvider(LocalDateTime.of(2024, 12, 29, 15, 30, 3));
        var processor = new ProcessorThrowExceptionInEvenSecond(dateTimeProvider);
        var message = new Message.Builder(1L).build();

        var result = processor.process(message);
        assertThat(result).isNotNull();
    }
}
