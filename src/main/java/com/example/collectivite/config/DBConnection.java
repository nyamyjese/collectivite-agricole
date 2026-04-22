package com.example.collectivite.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        try {
            String JDBC_URL = System.getenv("JDBC_URL");
            String user = System.getenv("user");
            String password = System.getenv("password");
            return DriverManager.getConnection(JDBC_URL, user, password);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
