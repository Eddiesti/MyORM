package ru.otus;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.StringJoiner;

class Executor {
    private Statement statement;
    private ReflectionDataHolder holder = ReflectionDataHolder.getClassInstance();

    <T extends DataSet> void save(T user) {
        Connection connection = Connecter.connect();
        try {
            statement = connection.createStatement();
            statement.executeUpdate(buildInsertSQL(user));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    <T extends DataSet> T load(long id, Class<T> clazz) {
        T object = null;
        try {
            ResultSet resultSet = statement.executeQuery(getById(id, clazz));
            Field[] fields = holder.getClassFields(clazz);
            object = clazz.getDeclaredConstructor().newInstance();
            resultSet.next();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(object, resultSet.getObject(field.getName()));
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | SQLException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return object;
    }

    private <T extends DataSet> String buildInsertSQL(T object) {
        String tableByClassName = object.getClass().getSimpleName();
        StringJoiner columns = new StringJoiner(", ");
        StringJoiner values = new StringJoiner(", ");
        Field[] fields = holder.getClassFields(object.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            columns.add(field.getName());
            try {
                if (field.getType() == String.class) {
                    values.add("'" + field.get(object) + "'");
                } else {
                    values.add(field.get(object).toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return String.format("insert into " + tableByClassName + "(%s) values(%s);", columns, values);
    }


    private <T extends DataSet> String getById(long id, Class<T> clazz) {
        String tableByClassName = clazz.getSimpleName();
        return "SELECT * FROM " + tableByClassName + " WHERE ID = " + id;
    }

    public  <T extends DataSet> String createTableByClassName(Class<T> clazz) {
        String tableName = clazz.getSimpleName();
        Connection connection = Connecter.connect();
        try {
            statement = connection.createStatement();
            statement.executeUpdate("create table if not exists " + tableName + "(id bigint auto_increment, name varchar(256), age int)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableName;
    }
}