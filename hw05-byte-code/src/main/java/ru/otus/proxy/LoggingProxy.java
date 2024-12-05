package ru.otus.proxy;

import ru.otus.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class LoggingProxy implements InvocationHandler {

    private final Object target;
    private final Set<Method> methodsWithLog;

    private LoggingProxy(Object target) {
        this.target = target;
        this.methodsWithLog = Arrays.stream(target.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Log.class))
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    public static <T> T createLoggingProxy(Object target, Class<T> interfaceType) {
        if (!interfaceType.isInterface()) {
            throw new IllegalArgumentException("Provided type must be an interface");
        }

        return (T) Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType},
                new LoggingProxy(target)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (methodsWithLog.contains(target.getClass().getMethod(method.getName(), method.getParameterTypes()))) {
            StringBuilder logMessage = new StringBuilder("executed method: ")
                    .append(method.getName());

            if (args != null && args.length > 0) {
                logMessage.append(", param");
                if (args.length > 1) {
                    logMessage.append("s");
                }
                logMessage.append(": ")
                        .append(String.join(", ",
                                Arrays.stream(args)
                                        .map(String::valueOf)
                                        .toArray(String[]::new)));
            } else {
                logMessage.append(", no params");
            }

            System.out.println(logMessage);
        }

        return method.invoke(target, args);
    }
}

