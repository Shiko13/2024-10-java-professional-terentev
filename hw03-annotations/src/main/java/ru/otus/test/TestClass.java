package ru.otus.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestClass {

    private static final Logger log = LoggerFactory.getLogger(TestClass.class);
    private int counter = 0;

    @Before
    public void setup() {
        counter = 10;
        log.info("Before method: Counter initialized to {}", counter);
    }

    @Test
    public void testIncrement() {
        log.info("Test method: Incrementing counter");
        counter++;
        log.info("Test method: Counter incremented to {}", counter);
        if (counter != 11) {
            throw new AssertionError("Counter expected to be 11 but was " + counter);
        }
    }

    @Test
    public void testDecrement() {
        log.info("Test method: Decrementing counter");
        counter--;
        log.info("Test method: Counter decremented to {}", counter);
        if (counter != 9) {
            throw new AssertionError("Counter expected to be 9 but was " + counter);
        }
    }

    @After
    public void tearDown() {
        log.info("After method: Counter reset to 0");
        counter = 0;
    }
}