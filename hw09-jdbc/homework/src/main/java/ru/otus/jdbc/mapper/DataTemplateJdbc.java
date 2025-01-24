package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntityClassMetaData<T> entityClassMetaData, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entityClassMetaData = entityClassMetaData;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        String sql = entitySQLMetaData.getSelectByIdSql();
        var fields = entityClassMetaData.getDeclaredFields().stream().map(Field::getName).toList();

        return dbExecutor.executeSelect(connection, sql, List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return mapResultSetToClient(rs, fields);
                }
                return null;
            } catch (SQLException e) {
                throw new RuntimeException("Error while processing ResultSet", e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        String sql = entitySQLMetaData.getSelectAllSql();
        var fields = entityClassMetaData.getDeclaredFields().stream().map(Field::getName).toList();

        return dbExecutor.executeSelect(connection, sql, List.of(), rs -> {
            List<T> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(mapResultSetToClient(rs, fields));
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error while processing ResultSet", e);
            }
            return result;
        }).orElseGet(ArrayList::new);
    }

    @Override
    public long insert(Connection connection, T client) {
        String sql = entitySQLMetaData.getInsertSql();
        List<String> fields = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .toList();
        return dbExecutor.executeStatement(connection, sql, extractFieldValues(client, fields));
    }

    @Override
    public void update(Connection connection, T client) {
        String sql = entitySQLMetaData.getUpdateSql();
        List<String> fields = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .toList();
        String field = entityClassMetaData.getIdField().getName();
        dbExecutor.executeStatement(connection, sql, extractFieldValuesWithId(client, fields, field));
    }

    private T createNewInstance() {
        try {
            return entityClassMetaData.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error creating entity instance", e);
        }
    }

    private void setEntityField(T client, String fieldName, Object value) {
        if (value == null) {
            return;
        }

        try {
            Field field = client.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(client, value);
        } catch (Exception e) {
            throw new RuntimeException("Error while setting fieldName: " + fieldName, e);
        }
    }

    private String capitalize(String field) {
        return field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    private Object getFieldValueFromResultSet(ResultSet rs, String field) {
        try {
            return rs.getObject(field);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch value for field: " + field, e);
        }
    }

    private List<Object> extractFieldValues(T client, List<String> fields) {
        var fieldValues = new ArrayList<>();
        Class<?> entityClass = client.getClass();

        try {
            for (String field : fields) {
                fieldValues.add(entityClass.getMethod("get" + capitalize(field)).invoke(client));
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No getter method for field", e);
        } catch (Exception e) {
            throw new RuntimeException("Error while extracting field values", e);
        }
        return fieldValues;
    }

    private T mapResultSetToClient(ResultSet rs, List<String> fields) {
        T client = createNewInstance();
        for (String field : fields) {
            Object value = getFieldValueFromResultSet(rs, field);
            setEntityField(client, field, value);
        }
        return client;
    }

    private List<Object> extractFieldValuesWithId(T client, List<String> fields, String field) {
        List<Object> fieldValues = extractFieldValues(client, fields);

        try {
            fieldValues.add(client.getClass().getMethod("get" + capitalize(field)).invoke(client));
        } catch (Exception e) {
            throw new RuntimeException("No getter method for ID field: " + field, e);
        }
        return fieldValues;
    }
}
