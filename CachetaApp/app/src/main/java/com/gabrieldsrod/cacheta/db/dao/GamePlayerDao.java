package com.gabrieldsrod.cacheta.db.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.entities.GamePlayer;
import com.gabrieldsrod.cacheta.entities.Player;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface GamePlayerDao {
    @Insert(onConflict = REPLACE)
    void insertAll(List<GamePlayer> gamePlayers);

    @Query("SELECT p.* FROM Player p " +
    "INNER JOIN GamePlayer gp ON gp.player_id = p.id " +
    "WHERE gp.game_id = :gameId")
    List<Player> getPlayersPerGame(int gameId);

    @Query("SELECT g.* FROM Game g INNER JOIN GamePlayer gp ON game_id = gp.game_id WHERE gp.player_id = :playerId ORDER BY g.start_time DESC")
    List<Game> getGamesPerPlayer(int playerId);

    @Query("SELECT g.* FROM Game g INNER JOIN GamePlayer gp ON game_id = gp.game_id WHERE gp.player_id = :playerId AND DATE(g.start_time) = :date ORDER BY g.start_time DESC")
    List<Game> getGamesPerPlayerOnDate(int playerId, LocalDate date);

    @Query("SELECT SUM(g.game_value / (SELECT COUNT(*) FROM GamePlayer WHERE game_id = g.id)) FROM Game g JOIN GamePlayer gp ON g.id = gp.game_id WHERE gp.player_id = :playerId")
    double getTotalPaidPerPlayer(int playerId);

    @Query("SELECT SUM(g.game_value / (SELECT COUNT(*) FROM GamePlayer WHERE game_id = g.id)) FROM Game g JOIN GamePlayer gp ON g.id = gp.game_id WHERE gp.player_id = :playerId  AND DATE(g.start_time) = :date")
    double getTotalPaidPerPlayerOnDate(int playerId, LocalDate date);

    @Query("SELECT SUM(g.duration_minutes) FROM Game g INNER JOIN GamePlayer gp ON g.id = gp.game_id WHERE gp.player_id = :playerId")
    int getTotalTimePerPlayer(int playerId);

    @Query("SELECT SUM(g.duration_minutes) FROM Game g INNER JOIN GamePlayer gp ON g.id = gp.game_id WHERE gp.player_id = :playerId AND DATE(g.start_time) = :date")
    int getTotalTimePerPlayerOnDate(int playerId, LocalDate date);
}