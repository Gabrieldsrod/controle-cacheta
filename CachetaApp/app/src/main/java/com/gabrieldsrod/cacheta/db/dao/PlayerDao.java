package com.gabrieldsrod.cacheta.db.dao;

import com.gabrieldsrod.cacheta.entities.Player;

import java.util.List;

public interface PlayerDao {

    void createPlayer(Player player);
    void deletePlayerById(int id);
    Player getPlayerById(int id);
    List<Player> getAllPlayers();
}
