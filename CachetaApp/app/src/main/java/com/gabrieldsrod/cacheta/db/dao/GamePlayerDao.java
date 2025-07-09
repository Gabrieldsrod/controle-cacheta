package com.gabrieldsrod.cacheta.db.dao;

import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.entities.Player;

import java.util.List;

public interface GamePlayerDao {

    void insertParticipants(int gameId, List<Player> players);
    List<Player> getPlayersPerGame(int gameId);
    List<Game> getGamesPerPlayer(int playerId);
    double getTotalPaidPerPlayer(int playerId);
    int getTotalTimePerPlayer(int playerId);
}