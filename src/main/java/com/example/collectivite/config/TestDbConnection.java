package com.example.collectivite.config;

import java.sql.Connection;

public class TestDbConnection {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = null;

        try {
            connection = dbConnection.getConnection();

            if (connection != null && !connection.isClosed()) {
                System.out.println("Connexion à la base de données réussie !");
            } else {
                System.out.println("Échec de la connexion.");
            }

        } catch (Exception e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
        }
    }
}
