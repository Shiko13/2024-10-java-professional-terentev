package ru.otus.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class TestClass {

    private static final Logger log = LoggerFactory.getLogger(TestClass.class);

    @Before
    public void setup() {
        log.info("Before method: Test setup.");
    }

    @Test
    public void testAddition() {
        log.info("Test method: Testing addition operation.");
        int a = 5;
        int b = 3;
        int result = a + b;
        int expected = 8;

        if (result != expected) {
            throw new AssertionError("Test failed: Expected " + expected + " but got " + result);
        } else {
            log.info("Test passed: Addition was correct.");
        }
    }

    @Test
    public void testSubtraction() {
        log.info("Test method: Testing subtraction operation.");
        int a = 5;
        int b = 3;
        int result = a - b;
        int expected = 2;

        if (result != expected) {
            throw new AssertionError("Test failed: Expected " + expected + " but got " + result);
        } else {
            log.info("Test passed: Subtraction was correct.");
        }
    }

    @Test
    public void testErrorScenario() {
        log.info("Test method: Testing an error scenario.");

        int a = 5;
        int b = 3;
        int result = a + b;
        int expected = 12;

        // Проверка
        if (result != expected) {
            throw new AssertionError("Test failed: Expected " + expected + " but got " + result);
        } else {
            log.info("Test passed: The error scenario was handled correctly.");
        }
    }

    @After
    public void tearDown() {
        log.info("After method: Test cleanup.");
    }
}