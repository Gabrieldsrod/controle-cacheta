package com.gabrieldsrod.controlecacheta.program;

import com.gabrieldsrod.controlecacheta.db.Database;
import com.gabrieldsrod.controlecacheta.db.dao.PlayerDao;
import com.gabrieldsrod.controlecacheta.db.dao.impl.DaoFactory;
import com.gabrieldsrod.controlecacheta.entities.Player;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = Database.getConnection()) {
            if (conn != null) {
                System.out.println("Conexão com o SQLite estabelecida com sucesso!");
                System.out.println("Arquivo do banco: cacheta.db");

                PlayerDao playerDao = DaoFactory.createPlayerDao();

                List<Player> players = new ArrayList<>();

                Player p1 = new Player("Pedro");
                Player p2 = new Player("Maria");
                Player p3 = new Player("Ana");
                Player p4 = new Player("Adamastor");

                players.add(p1);
                players.add(p2);
                players.add(p3);
                players.add(p4);

                for (Player p : players) {
                    playerDao.createPlayer(p);
                }

                Database.closeConnection();
            } else {
                System.out.println("Conexão retornou null.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
    }
}