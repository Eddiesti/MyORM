package ru.otus;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class ExecutorTest {
    private Executor executor = null;
    private UserDataSet roman = null;

    @Before
    public void init() {
        roman = new UserDataSet(1, "Roman");
        executor = new Executor();
        executor.createTableByClassName(roman.getClass());
    }

    @Test
    public void save() throws SQLException {
        Connection connection = Connecter.connect();
        Statement statement = connection.createStatement();
        Executor executor = new Executor();
        executor.save(roman);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM UserDataSet");
        resultSet.next();
        String name = (String) resultSet.getObject("name");
        Integer age = (Integer) resultSet.getObject("age");
        UserDataSet userDataSet = new UserDataSet(age, name);
        assertEquals(roman, userDataSet);
    }

    @Test
    public void load() {
        executor.save(roman);
        UserDataSet load = executor.load(1, roman.getClass());
        assertEquals(roman, load);
    }
}