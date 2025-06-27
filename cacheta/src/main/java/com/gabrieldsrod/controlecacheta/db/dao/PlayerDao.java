package com.gabrieldsrod.controlecacheta.db.dao;

import com.gabrieldsrod.controlecacheta.entities.Player;

import java.util.List;

public interface PlayerDao {

    void createPlayer(Player player);
    void deleteById(Integer id);
    Player getById(Integer id);
    List<Player> getAllPlayers();
}
