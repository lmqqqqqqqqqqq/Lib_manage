package com.example.javafx;

//database connection's information.
import java.sql.Connection;
import java.sql.DriverManager;

public class  DatabaseConnect{
    private static final String url = "jdbc:mysql://127.0.0.1:3306/test";
    private static final String DataUsername = "root";
    private static final String DataPassword = "1234";

    public Connection connect() throws Exception {
        return DriverManager.getConnection(url, DataUsername, DataPassword);
    }

    public static Connection getconnect() throws Exception {
        return DriverManager.getConnection(url, DataUsername, DataPassword);
    }
}
