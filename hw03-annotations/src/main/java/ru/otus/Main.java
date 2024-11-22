package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.test.TestClass;
import ru.otus.test.TestResult;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        TestResult testResult = new TestResult();

        Method beforeMethod = findBeforeMethod(testClass);
        Method afterMethod = findAfterMethod(testClass);
        List<Method> testMethods = findTestMethods(testClass);

        for (Method method : testMethods) {
            runTest(beforeMethod, afterMethod, method, testClass, testResult);
        }

        printTestStatistics(testResult);
    }

    private static Method findBeforeMethod(TestClass testClass) {
        for (Method method : testClass.getClass().getMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                return method;
            }
        }
        return null;
    }

    private static Method findAfterMethod(TestClass testClass) {
        for (Method method : testClass.getClass().getMethods()) {
            if (method.isAnnotationPresent(After.class)) {
                return method;
            }
        }
        return null;
    }

    private static List<Method> findTestMethods(TestClass testClass) {
        List<Method> testMethods = new ArrayList<>();
        for (Method method : testClass.getClass().getMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }
        return testMethods;
    }

    private static void runTest(Method beforeMethod, Method afterMethod, Method testMethod,
                                TestClass testClass, TestResult testResult) {
        try {
            if (beforeMethod != null) {
                beforeMethod.invoke(testClass);
            }

            testMethod.invoke(testClass);
            testResult.incrementPassedTests();
        } catch (Exception e) {
            testResult.incrementFailedTests();
            System.out.println("Test " + testMethod.getName() + " failed with exception: " + e.getMessage());
        } finally {
            if (afterMethod != null) {
                try {
                    afterMethod.invoke(testClass);
                } catch (Exception e) {
                    System.err.println("After method failed with exception: " + e.getMessage());
                }
            }
        }
    }

    private static void printTestStatistics(TestResult testResult) {
        System.out.println("Test statistics:");
        System.out.println("Total tests run: " + testResult.getTotalTests());
        System.out.println("Passed tests: " + testResult.getPassedTests());
        System.out.println("Failed tests: " + testResult.getFailedTests());
    }
}
