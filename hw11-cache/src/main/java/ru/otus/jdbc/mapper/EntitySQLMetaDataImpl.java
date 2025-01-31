package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public record EntitySQLMetaDataImpl<T>(EntityClassMetaData<T> entityClassMetaData) implements EntitySQLMetaData {

    @Override
    public String getSelectAllSql() {
        return String.format("SELECT * FROM %s", entityClassMetaData.getName().toLowerCase());
    }

    @Override
    public String getSelectByIdSql() {
        String table = entityClassMetaData.getName().toLowerCase();
        String column = entityClassMetaData.getIdField().getName().toLowerCase();

        return String.format("SELECT * FROM %s WHERE %s = ?", table, column);
    }

    @Override
    public String getInsertSql() {
        String table = entityClassMetaData.getName().toLowerCase();
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        String columns = fieldsWithoutId.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
        String values = fieldsWithoutId.stream()
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        return String.format("INSERT INTO %s (%s) VALUES (%s)", table, columns, values);
    }

    @Override
    public String getUpdateSql() {
        String table = entityClassMetaData.getName().toLowerCase();
        String column = entityClassMetaData.getIdField().getName().toLowerCase();
        String changes = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> field.getName().toLowerCase() + " = ?")
                .collect(Collectors.joining(", "));

        return String.format("UPDATE %s SET %s WHERE %s = ?", table, changes, column);
    }
}
