package ru.otus.impl;

import ru.otus.annotations.Log;
import ru.otus.interfaces.TestLoggingInterface;

public class TestLogging implements TestLoggingInterface {

    @Log
    @Override
    public void calculation(int param) {
        System.out.println("Calculation with one param and with annotation");
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        System.out.println("Calculation with two params and with annotation");
    }

    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println("Calculation with three params and without annotation");
    }
}
