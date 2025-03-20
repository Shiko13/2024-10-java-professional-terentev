package ru.otus;

import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                    .executor(executor)
                    .usePlaintext()
                    .build();

            var seqClient = new SequenceProvider(channel);
            seqClient.start();

            channel.shutdown();
        }
    }
}
