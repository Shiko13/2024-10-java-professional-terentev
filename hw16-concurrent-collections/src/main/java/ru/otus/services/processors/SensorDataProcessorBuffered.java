package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// Этот класс нужно реализовать
@SuppressWarnings({"java:S1068", "java:S125"})
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final BlockingQueue<SensorData> dataBuffer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.dataBuffer = new LinkedBlockingQueue<>();
    }

    @Override
    public void process(SensorData data) {
        try {
            dataBuffer.put(data);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (dataBuffer.size() >= bufferSize) {
            flush();
        }
    }

    public void flush() {
        List<SensorData> bufferedData = new ArrayList<>(dataBuffer.size());

        if (!dataBuffer.isEmpty()) {
            dataBuffer.drainTo(bufferedData);
        }

        if (!bufferedData.isEmpty()) {
            bufferedData.sort(Comparator.comparing(SensorData::getMeasurementTime));

            try {
                writer.writeBufferedData(bufferedData);
            } catch (Exception e) {
                log.error("Ошибка в процессе записи буфера", e);
            }
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}