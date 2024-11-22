package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.test.TestClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Method beforeMethod;
    private static Method afterMethod;
    private static final List<Method> testMethods = new ArrayList<>();

    public static void main(String[] args) {
        Class<?> testClass = TestClass.class;
        runTests(testClass);
    }

    public static void runTests(Class<?> testClass) {
        separateToTypes(testClass);

        for (Method testMethod : testMethods) {
            System.out.println("Running test: " + testMethod.getName());

            try {
                Object testInstance = testClass.getDeclaredConstructor().newInstance();

                if (beforeMethod != null) {
                    runMethod(beforeMethod, testInstance);
                }
                runMethod(testMethod, testInstance);
            } catch (Exception e) {
                System.out.println("Test " + testMethod.getName() + " failed with error: " + e.getCause());
            } finally {
                try {
                    if (afterMethod != null) {
                        Object testInstance = testClass.getDeclaredConstructor().newInstance();
                        runMethod(afterMethod, testInstance);
                    }
                } catch (Exception e) {
                    System.out.println("After method failed with error: " + e.getCause());
                }
            }
            System.out.println("Finished test: " + testMethod.getName() + "\n");
        }
    }

    private static void separateToTypes(Class<?> testClass) {
        for (Method method : testClass.getMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            } else if (method.isAnnotationPresent(Before.class)) {
                if (beforeMethod != null) {
                    throw new IllegalStateException("Multiple @Before methods are not allowed");
                }
                beforeMethod = method;
            } else if (method.isAnnotationPresent(After.class)) {
                if (afterMethod != null) {
                    throw new IllegalStateException("Multiple @After methods are not allowed");
                }
                afterMethod = method;
            }
        }

        if (testMethods.isEmpty()) {
            System.out.println("No test methods found in class: " + testClass.getName());
        }
    }

    private static void runMethod(Method method, Object testInstance) throws InvocationTargetException, IllegalAccessException {
        System.out.println("Executing method: " + method.getName());
        method.invoke(testInstance);
    }
}