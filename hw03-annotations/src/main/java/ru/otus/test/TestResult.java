package ru.otus.test;

public class TestResult {

    private int passedTests = 0;
    private int failedTests = 0;
    private int totalTests = 0;

    public void incrementPassedTests() {
        passedTests++;
        totalTests++;
    }

    public void incrementFailedTests() {
        failedTests++;
        totalTests++;
    }

    public int getPassedTests() {
        return passedTests;
    }

    public int getFailedTests() {
        return failedTests;
    }

    public int getTotalTests() {
        return totalTests;
    }
}
