package com.gabrieldsrod.controlecacheta.db.dao;

import com.gabrieldsrod.controlecacheta.entities.Player;

import java.util.List;

public interface PlayerDao {

    void createPlayer(Player player);
    void deleteById(int id);
    Player getById(int id);
    List<Player> getAllPlayers();
}
