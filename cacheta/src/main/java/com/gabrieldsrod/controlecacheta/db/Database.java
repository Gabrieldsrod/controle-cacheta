package com.gabrieldsrod.controlecacheta.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection conn = null;

    private static final String DB_URL = "jdbc:sqlite:cacheta.db";

    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                throw new DatabaseException("Erro ao conectar: " + e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DatabaseException("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}