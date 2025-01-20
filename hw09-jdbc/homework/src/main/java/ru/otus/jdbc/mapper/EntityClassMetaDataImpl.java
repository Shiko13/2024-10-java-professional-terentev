package ru.otus.jdbc.mapper;

import ru.otus.crm.model.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;
    private final Constructor<T> constructor;
    private final Field fieldWithIdAnnotation;
    private final List<Field> declaredFields;
    private final List<Field> fieldsWithoutIdAnnotation;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        try {
            this.constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Failed to find the constructor for class: " + clazz.getName(), e);
        }

        this.declaredFields = List.of(clazz.getDeclaredFields());

        var fieldsWithIdAnnotation = declaredFields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .toList();
        if (fieldsWithIdAnnotation.isEmpty()) {
            throw new RuntimeException("Class " + clazz.getSimpleName() + " does not contain @Id field.");
        } else if (fieldsWithIdAnnotation.size() > 1) {
            throw new RuntimeException("Multiple @Id fields found in class " + clazz.getSimpleName());
        }
        this.fieldWithIdAnnotation = fieldsWithIdAnnotation.getFirst();

        this.fieldsWithoutIdAnnotation = this.declaredFields.stream()
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return fieldWithIdAnnotation;
    }

    @Override
    public List<Field> getDeclaredFields() {
        return this.declaredFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return this.fieldsWithoutIdAnnotation;
    }
}
