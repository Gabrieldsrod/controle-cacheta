package com.gabrieldsrod.cacheta.entities.service;

import androidx.annotation.Nullable;

import com.gabrieldsrod.cacheta.db.dao.GamePlayerDao;
import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.entities.GamePlayer;
import com.gabrieldsrod.cacheta.entities.Player;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GamePlayerService {

    private final GamePlayerDao gamePlayerDao;

    public GamePlayerService(GamePlayerDao gamePlayerDao) {
        this.gamePlayerDao = gamePlayerDao;
    }

    public void addPlayersToGame(List<GamePlayer> gamePlayers) {
        gamePlayerDao.insertAll(gamePlayers);
    }

    public void insertParticipants(int gameId, List<Player> players) {
        List<GamePlayer> list = new ArrayList<>();
        for (Player player : players) {
            list.add(new GamePlayer(gameId, player.getId()));
        }
        gamePlayerDao.insertAll(list);
    }

    public List<Player> getPlayersPerGame(int gameId) {
        return gamePlayerDao.getPlayersPerGame(gameId);
    }

    public List<Game> getGamesPerPlayer(int playerId) {
        return gamePlayerDao.getGamesPerPlayer(playerId);
    }

    public List<Game> getGamesPerPlayer(int playerId, LocalDate date) {
        return gamePlayerDao.getGamesPerPlayerOnDate(playerId, date);
    }

    public List<Game> getGamesPerPlayerToday(int playerId) {
        return getGamesPerPlayer(playerId, LocalDate.now());
    }

    public double getTotalPaidByPlayer(int playerId) {
        return gamePlayerDao.getTotalPaidPerPlayer(playerId);
    }

    public double getTotalPaidByPlayer(int playerId, LocalDate date) {
        return gamePlayerDao.getTotalPaidPerPlayerOnDate(playerId, date);
    }

    public double getTotalPaidByPlayerToday(int playerId) {
        return getTotalPaidByPlayer(playerId, LocalDate.now());
    }

    public int getTotalPlayTimeForPlayer(int playerId) {
        return gamePlayerDao.getTotalTimePerPlayer(playerId);
    }

    public int getTotalPlayTimeForPlayer(int playerId, LocalDate date) {
        return gamePlayerDao.getTotalTimePerPlayerOnDate(playerId, date);
    }

    public int getTotalPlayTimeForPlayerToday(int playerId) {
        return getTotalPlayTimeForPlayer(playerId, LocalDate.now());
    }

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public Integer getTopPlayerIdOnDate(int tableId, LocalDate date) {
        String startIso = date.atStartOfDay().format(FMT);
        String endIso   = date.plusDays(1).atStartOfDay().format(FMT);
        return gamePlayerDao.getTopPlayerIdOnDate(tableId, startIso, endIso);
    }

    @Nullable
    public Integer getTopPlayerIdToday(int tableId) {
        return getTopPlayerIdOnDate(tableId, LocalDate.now());
    }

    public int getTopPlayerMinutesOnDate(int tableId, int playerId, LocalDate date) {
        String startIso = date.atStartOfDay().format(FMT);
        String endIso   = date.plusDays(1).atStartOfDay().format(FMT);
        return gamePlayerDao.getPlayerMinutesForTableOnDate(tableId, playerId, startIso, endIso);
    }

    public int getTopPlayerMinutesToday(int tableId, int playerId) {
        return getTopPlayerMinutesOnDate(tableId, playerId, LocalDate.now());
    }
}
