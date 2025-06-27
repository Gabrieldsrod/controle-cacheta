package com.gabrieldsrod.controlecacheta.program;

import com.gabrieldsrod.controlecacheta.db.Database;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = Database.getConnection()) {
            if (conn != null) {
                System.out.println("Conexão com o SQLite estabelecida com sucesso!");
                System.out.println("Arquivo do banco: cacheta.db");
                Database.closeConnection();
            } else {
                System.out.println("Conexão retornou null.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
    }
}