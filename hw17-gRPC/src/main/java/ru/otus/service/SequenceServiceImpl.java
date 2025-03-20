package ru.otus.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.otus.SequenceResponse;
import ru.otus.SequenceServiceGrpc;
import ru.otus.SequenceMessage;

import java.util.stream.IntStream;

@Slf4j
public class SequenceServiceImpl extends SequenceServiceGrpc.SequenceServiceImplBase {

    @Override
    public void generateSequence(SequenceMessage message, StreamObserver<SequenceResponse> responseObserver) {
        int start = message.getFirst();
        int last = message.getLast();

        IntStream.range(start, last).forEach(val -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error("Sequence generation was interrupted", e);
            }
            var response = SequenceResponse.newBuilder().setResponse(val).build();
            responseObserver.onNext(response);
        });

        responseObserver.onCompleted();
    }
}
