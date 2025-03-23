package ru.otus;

import io.grpc.ServerBuilder;
import ru.otus.service.SequenceServiceImpl;

import java.io.IOException;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {
        var remoteSeqService = new SequenceServiceImpl();

        var server =
                ServerBuilder.forPort(SERVER_PORT).addService(remoteSeqService).build();
        server.start();
        System.out.println("Server waiting for client connections...");

        server.awaitTermination();
    }
}
