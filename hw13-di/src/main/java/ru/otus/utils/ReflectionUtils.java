package ru.otus.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

    public static List<Method> findAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .toList();
    }

    public static <T> T createInstance(Class<T> clazz, Object... args) {
        try {
            if (args.length == 0) {
                return clazz.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] paramTypes = Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
                return clazz.getDeclaredConstructor(paramTypes).newInstance(args);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании экземпляра класса " + clazz.getName(), e);
        }
    }

    public static Object callMethod(Object instance, Method method, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Ошибка при вызове метода " + method.getName(), e);
        }
    }
}
