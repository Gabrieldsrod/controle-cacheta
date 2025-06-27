package com.gabrieldsrod.controlecacheta.db.dao;

import com.gabrieldsrod.controlecacheta.entities.Game;
import com.gabrieldsrod.controlecacheta.entities.Player;

import java.util.List;

public interface GamePlayerDao {
    void insertParticipants(int gameId, List<Integer> playerIds);
    List<Player> getPlayersPerGame(int gameId);
    List<Game> getGamesPerPlayer(int playerId);
    double getTotalPaidPerPlayer(int playerId);
    int getTotalTimePerPlayer(int playerId);
}