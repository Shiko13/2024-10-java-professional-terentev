package ru.otus;

import ru.otus.test.TestClass;
import ru.otus.test.TestRunner;

public class Main {

    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        TestRunner testRunner = new TestRunner();

        testRunner.runTests(testClass);
    }
}
