package com.gabrieldsrod.controlecacheta.program;

import com.gabrieldsrod.controlecacheta.db.Database;
import com.gabrieldsrod.controlecacheta.db.dao.PlayerDao;
import com.gabrieldsrod.controlecacheta.db.dao.TableDao;
import com.gabrieldsrod.controlecacheta.db.dao.impl.DaoFactory;
import com.gabrieldsrod.controlecacheta.entities.Player;
import com.gabrieldsrod.controlecacheta.entities.Table;

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
                TableDao tableDao = DaoFactory.createTableDao();

                List<Player> players = new ArrayList<>();

                List<Table> tables = new ArrayList<>();

//                Player p1 = new Player("Pedro");
//                Player p2 = new Player("Maria");
//                Player p3 = new Player("Ana");
//                Player p4 = new Player("Adamastor");
//
//                players.add(p1);
//                players.add(p2);
//                players.add(p3);
//                players.add(p4);
//
//                for (Player p : players) {
//                    playerDao.createPlayer(p);
//                }
//
//                Table t1 = new Table();
//                Table t2 = new Table();
//                Table t3 = new Table();
//
//                tables.add(t1);
//                tables.add(t2);
//                tables.add(t3);
//
//                for (Table t : tables) {
//                    tableDao.createTable(t);
//                }

//                playerDao.deleteById(3);
//                tableDao.deleteById(1);

//                players = playerDao.getAllPlayers();
//                for (Player p : players) {
//                    System.out.println(p);
//                }
//                System.out.println();
//                tables = tableDao.getAllTables();
//                for (Table tb : tables) {
//                    System.out.println(tb);
//                }
//                System.out.println();
//                System.out.println(playerDao.getById(2));
//                System.out.println(tableDao.getById(3));
//
//                System.out.println();
//                System.out.println(tableDao.getByStatus("ocupada"));

//                tableDao.updateTableStatus(2, "ocupada");
//                System.out.println(tableDao.getByStatus("ocupada"));


                Database.closeConnection();
            } else {
                System.out.println("Conexão retornou null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}