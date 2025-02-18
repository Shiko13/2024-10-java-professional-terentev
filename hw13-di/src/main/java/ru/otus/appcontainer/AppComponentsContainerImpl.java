package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.util.*;

import static ru.otus.utils.ReflectionUtils.*;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> matchingComponents = appComponents.stream()
                .filter(component -> componentClass.isAssignableFrom(component.getClass()))
                .toList();

        if (matchingComponents.isEmpty()) {
            throw new RuntimeException(String.format("Компонент типа %s не найден", componentClass));
        }
        if (matchingComponents.size() > 1) {
            throw new RuntimeException(String.format("Обнаружены несколько реализаций для %s", componentClass));
        }
        return (C) matchingComponents.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object component = appComponentsByName.get(componentName);
        if (component == null) {
            throw new RuntimeException(String.format("Компонент с именем '%s' не найден", componentName));
        }
        return (C) component;
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        findAnnotatedMethods(configClass, AppComponent.class).stream()
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .forEach(method -> {
                    var componentName = method.getAnnotation(AppComponent.class).name();
                    var componentInstance = callMethod(createInstance(configClass), method, resolveDependencies(method.getParameterTypes()));
                    registerComponent(componentName, componentInstance);
                });
    }

    private Object[] resolveDependencies(Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes)
                .map(this::getAppComponent)
                .toArray();
    }

    private void registerComponent(String componentName, Object componentInstance) {
        if (isComponentExists(componentName)) {
            throw new RuntimeException(String.format("Имя %s уже занято", componentName));
        }
        appComponentsByName.put(componentName, componentInstance);
        appComponents.add(componentInstance);
    }


    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private boolean isComponentExists(String componentName) {
        return appComponentsByName.containsKey(componentName);
    }
}
