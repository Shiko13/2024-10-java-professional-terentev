package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class NumberSwitcher {

    private static final Logger logger = LoggerFactory.getLogger(NumberSwitcher.class);
    private static final String MAIN_WORKER = "main-worker";
    private static final String AUX_WORKER = "aux-worker";
    private static final List<Integer> sequence = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 9, 8, 7, 6, 5, 4, 3, 2);
    private String activeWorker = MAIN_WORKER;

    public void startSwitching() {
        new Thread(() -> processSequence(new ArrayDeque<>(sequence), AUX_WORKER), MAIN_WORKER).start();
        new Thread(() -> processSequence(new ArrayDeque<>(sequence), MAIN_WORKER), AUX_WORKER).start();
    }

    private synchronized void processSequence(Deque<Integer> sequence, String nextWorker) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (!activeWorker.equals(Thread.currentThread().getName())) {
                    this.wait();
                }

                Integer current = sequence.poll();
                logger.info("Current value: {}", current);

                sequence.add(current);
                activeWorker = nextWorker;

                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
}