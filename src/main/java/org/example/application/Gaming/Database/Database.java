package org.example.application.Gaming.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database implements DatabaseInterface{

    private static Database database;

    private static String DB_URL = "jdbc:postgresql://localhost:5431/swe1db";

    private static String DB_USER = "swe1user";

    private static String DB_PW = "swe1pw";

    private Database(){
    }

    public static Database getDatabase() {
        if (Database.database == null) {
            Database.database = new Database();
        }
        return Database.database;
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL,DB_USER,DB_PW);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
