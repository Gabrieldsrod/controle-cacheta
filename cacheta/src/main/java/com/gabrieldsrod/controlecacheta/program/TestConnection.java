package com.gabrieldsrod.controlecacheta.program;

import com.gabrieldsrod.controlecacheta.db.Database;
import com.gabrieldsrod.controlecacheta.db.dao.GameDao;
import com.gabrieldsrod.controlecacheta.db.dao.PlayerDao;
import com.gabrieldsrod.controlecacheta.db.dao.TableDao;
import com.gabrieldsrod.controlecacheta.db.dao.impl.DaoFactory;
import com.gabrieldsrod.controlecacheta.entities.Game;
import com.gabrieldsrod.controlecacheta.entities.Player;
import com.gabrieldsrod.controlecacheta.entities.Table;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
                GameDao gameDao = DaoFactory.createGameDao();

                List<Player> players = new ArrayList<>();
                List<Table> tables = new ArrayList<>();
                List<Game> games = new ArrayList<>();

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


//                Table table = new Table();
//                table = tableDao.getTableById(3);
//
//                Game game = new Game();
//                game.setTable(table);
//                game.setStartTime(LocalDateTime.parse("2025-06-02T12:00:00"));
//                game.setEndTime(LocalDateTime.parse("2025-06-02T13:00:00"));
//                game.setDurationMinutes(60);
//                game.setGameValue(15.0);
//
//                gameDao.createGame(game);
//                System.out.println(game);

//                Game IdGame = gameDao.getGameById(6);
//                System.out.println(IdGame);
//
//                System.out.println();
//                games = gameDao.getAllGames();
//                for(Game g : games) {
//                    System.out.println(g);
//                }

                // Testar getGamesOnDate
                LocalDate data = LocalDate.of(2025, 6, 2);
                List<Game> gamesOnDate = gameDao.getGamesOnDate(data);
                System.out.println("Jogos em " + data + ":");
                for (Game g : gamesOnDate) {
                    System.out.println(g);
                }
                System.out.println();

// Testar getGamesPerTable
                int tableId = 1;
                List<Game> gamesPerTable = gameDao.getGamesPerTable(tableId);
                System.out.println("Jogos na mesa " + tableId + ":");
                for (Game g : gamesPerTable) {
                    System.out.println(g);
                }
                System.out.println();

// Testar getTotalRaised
                double totalRaised = gameDao.getTotalRaised();
                System.out.println("Total arrecadado em todas as partidas: R$ " + String.format("%.2f", totalRaised));
                System.out.println();

// Testar getTotalRaisedPerTable
                double totalRaisedPerTable = gameDao.getTotalRaisedPerTable(tableId);
                System.out.println("Total arrecadado na mesa " + tableId + ": R$ " + String.format("%.2f", totalRaisedPerTable));
                System.out.println();

// Testar getTotalRaisedOnDay
                double totalRaisedOnDay = gameDao.getTotalRaisedOnDay(data);
                System.out.println("Total arrecadado no dia " + data + ": R$ " + String.format("%.2f", totalRaisedOnDay));
                System.out.println();

                System.out.println();

                Database.closeConnection();
            } else {
                System.out.println("Conexão retornou null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}