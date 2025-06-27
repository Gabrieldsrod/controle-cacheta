package com.gabrieldsrod.controlecacheta.db.dao.impl;

import com.gabrieldsrod.controlecacheta.db.dao.GamePlayerDao;
import com.gabrieldsrod.controlecacheta.entities.Game;
import com.gabrieldsrod.controlecacheta.entities.Player;

import java.sql.Connection;
import java.util.List;

public class GamePlayerDaoJDBC implements GamePlayerDao {

    private Connection conn;

    public GamePlayerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertParticipants(int gameId, List<Integer> playerIds) {

    }

    @Override
    public List<Player> getPlayersPerGame(int gameId) {
        return List.of();
    }

    @Override
    public List<Game> getGamesPerPlayer(int playerId) {
        return List.of();
    }

    @Override
    public double getTotalPaidPerPlayer(int playerId) {
        return 0;
    }

    @Override
    public int getTotalTimePerPlayer(int playerId) {
        return 0;
    }
}
