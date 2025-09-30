package com.example.javafx;

//database connection's information.
import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class  DatabaseConnect{

    private static final Dotenv dotenv = Dotenv.load();

    private static final String url = dotenv.get("DB_URL");
    private static final String DataUsername = dotenv.get("DB_USERNAME");
    private static final String DataPassword = dotenv.get("DB_PASSWORD");

    public Connection connect() throws Exception {
        return DriverManager.getConnection(url, DataUsername, DataPassword);
    }

    public static Connection getconnect() throws Exception {
        return DriverManager.getConnection(url, DataUsername, DataPassword);
    }
}
