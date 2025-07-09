package com.gabrieldsrod.cacheta.program;

import com.gabrieldsrod.cacheta.db.Database;
import com.gabrieldsrod.cacheta.db.dao.GameDao;
import com.gabrieldsrod.cacheta.db.dao.GamePlayerDao;
import com.gabrieldsrod.cacheta.db.dao.PlayerDao;
import com.gabrieldsrod.cacheta.db.dao.TableDao;
import com.gabrieldsrod.cacheta.db.dao.impl.DaoFactory;
import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.entities.Player;
import com.gabrieldsrod.cacheta.entities.Table;

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
                GameDao gameDao = DaoFactory.createGameDao();
                GamePlayerDao gamePlayerDao = DaoFactory.createGamePlayerDao();

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

//                // Testar getGamesOnDate
//                LocalDate data = LocalDate.of(2025, 6, 2);
//                List<Game> gamesOnDate = gameDao.getGamesOnDate(data);
//                System.out.println("Jogos em " + data + ":");
//                for (Game g : gamesOnDate) {
//                    System.out.println(g);
//                }
//                System.out.println();
//
//// Testar getGamesPerTable
//                int tableId = 1;
//                List<Game> gamesPerTable = gameDao.getGamesPerTable(tableId);
//                System.out.println("Jogos na mesa " + tableId + ":");
//                for (Game g : gamesPerTable) {
//                    System.out.println(g);
//                }
//                System.out.println();
//
//// Testar getTotalRaised
//                double totalRaised = gameDao.getTotalRaised();
//                System.out.println("Total arrecadado em todas as partidas: R$ " + String.format("%.2f", totalRaised));
//                System.out.println();
//
//// Testar getTotalRaisedPerTable
//                double totalRaisedPerTable = gameDao.getTotalRaisedPerTable(tableId);
//                System.out.println("Total arrecadado na mesa " + tableId + ": R$ " + String.format("%.2f", totalRaisedPerTable));
//                System.out.println();
//
//// Testar getTotalRaisedOnDay
//                double totalRaisedOnDay = gameDao.getTotalRaisedOnDay(data);
//                System.out.println("Total arrecadado no dia " + data + ": R$ " + String.format("%.2f", totalRaisedOnDay));
//                System.out.println();


                int gameId = 1; // Altere para o ID que quiser testar
                List<Player> jogadoresNoGame = gamePlayerDao.getPlayersPerGame(gameId);
                System.out.println("Jogadores no jogo ID " + gameId + ":");
                for (Player p : jogadoresNoGame) {
                    System.out.println(p.getId() + " - " + p.getName());
                }
                System.out.println();

                int playerId = 2; // Altere para o ID do jogador que quiser testar
                List<Game> gamesDoJogador = gamePlayerDao.getGamesPerPlayer(playerId);
                System.out.println("Partidas do jogador ID " + playerId + ":");
                for (Game g : gamesDoJogador) {
                    System.out.println("Game ID: " + g.getId() + ", Data: " + g.getStartTime());
                }
                System.out.println();

                int playerIdTempo = 2; // Altere para o ID do jogador que quiser testar
                int tempoTotal = gamePlayerDao.getTotalTimePerPlayer(playerIdTempo);
                System.out.println("Tempo total jogado pelo jogador ID " + playerIdTempo + ": " + tempoTotal + " minutos");
                System.out.println();

                int playerIdPagamento = 2; // Altere para o ID do jogador que quiser testar
                double totalPago = gamePlayerDao.getTotalPaidPerPlayer(playerIdPagamento);
                System.out.println("Total a pagar pelo jogador ID " + playerIdPagamento + ": R$ " + String.format("%.2f", totalPago));
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