package ru.otus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 class Connecter {
     static Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/db_example", "root", "root");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
