package com.gabrieldsrod.controlecacheta.db.dao;

import com.gabrieldsrod.controlecacheta.entities.Player;

import java.util.List;

public interface PlayerDao {

    void createPlayer(Player player);
    void deletePlayerById(int id);
    Player getPlayerById(int id);
    List<Player> getAllPlayers();
}
