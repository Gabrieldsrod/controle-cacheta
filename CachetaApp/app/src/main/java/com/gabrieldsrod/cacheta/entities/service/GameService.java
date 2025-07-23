package com.gabrieldsrod.cacheta.entities.service;

import com.gabrieldsrod.cacheta.db.dao.GameDao;
import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.entities.TablePayment;

import java.time.LocalDate;
import java.util.List;

public class GameService {

    private GameDao gameDao;

    public void createGame(Game game) {
        gameDao.createGame(game);
    }
    public Game getGameById(int id) {
        Game game = gameDao.getGameById(id);
        if (game == null) {
            throw new IllegalArgumentException("Jogador com ID " + id + " não encontrado.");
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
}
