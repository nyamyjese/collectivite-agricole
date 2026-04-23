package com.example.collectivite.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static DBConnection instance;

    private DBConnection() {}

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            String jdbcUrl  = System.getenv("JDBC_URL");
            String user     = System.getenv("user");
            String password = System.getenv("password");
            return DriverManager.getConnection(jdbcUrl, user, password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }
}