package com.gabrieldsrod.controlecacheta.db.dao.impl;

import com.gabrieldsrod.controlecacheta.db.dao.GameDao;
import com.gabrieldsrod.controlecacheta.entities.Game;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class GameDaoJDBC implements GameDao {

    private Connection conn;

    public GameDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int createGame(Game game) {
        return 0;
    }

    @Override
    public Game getGameById(int id) {
        return null;
    }

    @Override
    public List<Game> getAllGames() {
        return List.of();
    }

    @Override
    public List<Game> getGamesOnDate(LocalDate date) {
        return List.of();
    }

    @Override
    public List<Game> getGamesOnDate(LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public List<Game> getGamesPerTable(int tableId) {
        return List.of();
    }

    @Override
    public double getTotalRaised() {
        return 0;
    }

    @Override
    public double getTotalRaisedPerTable(int id) {
        return 0;
    }

    @Override
    public double getTotalRaisedOnDay(int id) {
        return 0;
    }
}
