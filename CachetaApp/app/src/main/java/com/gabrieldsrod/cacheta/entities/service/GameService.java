package com.gabrieldsrod.cacheta.entities.service;

import com.gabrieldsrod.cacheta.db.dao.GameDao;
import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.dto.TablePayment;

import java.time.LocalDate;
import java.util.List;

public class GameService {

    private final GameDao gameDao;

    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public long createGame(Game game) {
        return gameDao.createGame(game);
    }

    public int updateGame(Game game) {
        return gameDao.updateGame(game);
    }

    public Game getGameById(int id) {
        Game game = gameDao.getGameById(id);
        if (game == null) {
            throw new IllegalArgumentException("Jogo com ID " + id + " não encontrado.");
        }
        return game;
    }

    public Game getLastGameByTable(int tableId) {
        Game game = gameDao.getLastGameByTable(tableId);
        if (game == null) {
            throw new IllegalArgumentException("Jogo com mesa de ID " + tableId + " não encontrado.");
        }
        return game;
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
        return gameDao.getTotalRaisedOnDate(LocalDate.now());
    }

    public List<TablePayment> getTotalRaisedPerTable() {
        return gameDao.getTotalRaisedPerTable();
    }

    public List<TablePayment> getTotalRaisedPerTable(LocalDate date) {
        return gameDao.getTotalRaisedPerTableOnDate(date);
    }
    public double getTotalRaisedPerTable(int tableNumber) {
        return gameDao.getTotalRaisedPerTableId(tableNumber);
    }

    public double getTotalRaisedPerTable(int tableNumber, LocalDate date) {
        return gameDao.getTotalRaisedPerTableIdOnDate(tableNumber, date);
    }

    public double getTotalRaisedPerTableToday(int tableNumber) {
        return getTotalRaisedPerTable(tableNumber, LocalDate.now());
    }

    public int getTotalOccupiedTables(LocalDate date) {
        return gameDao.getTotalOccupiedTables(date);
    }

    public int getTotalOccupiedTablesToday() {
        return getTotalOccupiedTables(LocalDate.now());
    }
}
