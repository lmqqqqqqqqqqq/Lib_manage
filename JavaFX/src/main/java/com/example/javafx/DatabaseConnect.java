package com.example.javafx;

//database connection's information.
import java.sql.Connection;
import java.sql.DriverManager;

public class  DatabaseConnect{
    private static final String url = "jdbc:mysql://127.0.0.1:3306/login_schema";
    private static final String DataUsername = "root";
    private static final String DataPassword = "12345678";

    public Connection connect() throws Exception {
        return DriverManager.getConnection(url, DataUsername, DataPassword);
    }
}
