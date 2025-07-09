package com.gabrieldsrod.cacheta.service;

import com.gabrieldsrod.cacheta.db.dao.GameDao;
import com.gabrieldsrod.cacheta.db.dao.GamePlayerDao;
import com.gabrieldsrod.cacheta.db.dao.TableDao;
import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.entities.Player;
import com.gabrieldsrod.cacheta.entities.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameService {
    private List<Table> tables;
    private double pricePerHour = 15.00;

    private TableDao tableDao;
    private GameDao gameDao;
    private GamePlayerDao gamePlayerDao;

    public GameService(TableDao tableDao, GameDao gameDao, GamePlayerDao gamePlayerDao) {
        this.tables = new ArrayList<>();
        this.tableDao = tableDao;
        this.gameDao = gameDao;
        this.gamePlayerDao = gamePlayerDao;
    }

    public void loadTables() {
        this.tables = tableDao.getAllTables();
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public List<Game> getFinishedGames() {
        return gameDao.getAllGames();
    }

    public List<Game> getTodayGames() {
        return gameDao.getGamesOnDate(LocalDate.now());
    }

    public List<Game> getDateGames(LocalDate date) {
        return gameDao.getGamesOnDate(date);
    }

    public List<Game> getGamesPerTable(int tableNumber) {
        return gameDao.getGamesPerTable(tableNumber);
    }

    public double getTotalRaised() {
        return gameDao.getTotalRaised();
    }

    public double getTotalRaisedToday() {
        return gameDao.getTotalRaisedOnDay(LocalDate.now());
    }

    public double getTotalRaisedPerTable(int tableNumber) {
        return gameDao.getTotalRaisedPerTable(tableNumber);
    }

    public void startGame(int tableNumber) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableNumber && table.getStatus().equals("Livre")) {
                LocalDateTime startTime = LocalDateTime.now();
                String status = "Ocupada";
                table.setStatus(status);
                table.setStartTime(startTime);

                tableDao.updateTableStatus(tableNumber, status);
                tableDao.updateTableStartTime(tableNumber, startTime);

                return;
            }
        }
        throw new IllegalArgumentException("Mesa não encontrada ou já está ocupada.");
    }

    public void endGame(int tableNumber) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableNumber && table.getStatus().equals("Ocupada")) {
                if (table.getStartTime() == null) {
                    throw new IllegalStateException("Mesa não iniciada corretamente.");
                }

                LocalDateTime endTime = LocalDateTime.now();
                int duration = table.getDurationMinutes(endTime);

                Game game = new Game(
                        table,
                        table.getStartTime(),
                        endTime,
                        duration,
                        0.0, // valor temporário, será calculado depois
                        new ArrayList<>(table.getPlayers())
                );


                double valorTotal = game.calculateGameValue(pricePerHour);
                game.setGameValue(valorTotal);

                for (Player player : table.getPlayers()) {
                    player.calculatePlayerPayments(pricePerHour, duration);
                }

                gameDao.createGame(game);
                gamePlayerDao.insertParticipants(game.getId(), game.getPlayers());

                tableDao.updateTableStatus(tableNumber, "Livre");
                tableDao.updateTableStartTime(tableNumber, null);

                table.setStatus("Livre");
                table.clearPlayers();
                table.setStartTime(null);

                System.out.println("Partida encerrada na mesa " + tableNumber + " com sucesso.");
                return;
            }
        }
        throw new IllegalArgumentException("Mesa não encontrada ou já está livre.");
    }
}
