package ru.otus;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SequenceProvider {

    private final SequenceServiceGrpc.SequenceServiceStub stub;
    private final AtomicInteger counter = new AtomicInteger(0);

    public SequenceProvider(ManagedChannel channel) {
        stub = SequenceServiceGrpc.newStub(channel);
    }

    public void start() {
        log.info("Starting sequence provider...");

        var sequenceRequest = SequenceMessage
                .newBuilder()
                .setFirst(0)
                .setLast(60)
                .build();

        startSequenceAccept(sequenceRequest);
        loop();
        log.info("Finishing sequence provider...");
    }

    private void loop() {
        try {
            while (counter.get() < 50) {
                Thread.sleep(1000);
                log.info("Current value: {}", counter.get() + 1);
                counter.incrementAndGet();
            }
        } catch (InterruptedException e) {
            log.error("Client loop was interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    private void startSequenceAccept(SequenceMessage sequenceRequest) {
        stub.generateSequence(sequenceRequest, new StreamObserver<>() {

            @Override
            public void onNext(SequenceResponse sequenceResponse) {
                int receivedValue = sequenceResponse.getResponse();
                log.info("New value: {}", receivedValue);
                counter.addAndGet(receivedValue);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Error while accepting stream", throwable);
            }

            @Override
            public void onCompleted() {
                log.info("Sequence receiving completed");
            }
        });
    }
}
