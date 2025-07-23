package com.gabrieldsrod.cacheta.entities.service;

import com.gabrieldsrod.cacheta.db.dao.PlayerDao;
import com.gabrieldsrod.cacheta.entities.Player;

import java.util.List;

public class PlayerService {

    private final PlayerDao playerDao;

    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public void createPlayer(Player player) {
        playerDao.createPlayer(player);
    }

    public Player getPlayerById(int id) {
        Player player = playerDao.getPlayerById(id);
        if (player == null) {
            throw new IllegalArgumentException("Jogador com ID " + id + " não encontrado.");
        }
        return player;
    }

    public List<Player> getAllPlayers() {
        return playerDao.getAllPlayers();
    }

    public int countPlayers() {
        return getAllPlayers().size();
    }

    public boolean updatePlayerName(int id, String newName) {
        playerDao.updatePlayer(id, newName);
        return true;
    }

    public boolean deletePlayer(int id) {
        playerDao.deletePlayerById(id);
        return true;
    }
}

