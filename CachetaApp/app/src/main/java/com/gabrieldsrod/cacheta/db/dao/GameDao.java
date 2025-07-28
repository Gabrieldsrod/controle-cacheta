package com.gabrieldsrod.cacheta.db.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.dto.TablePayment;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface GameDao {

    @Insert(onConflict = REPLACE)
    long createGame(Game game);

    @Update
    int updateGame(Game game);

    @Query("SELECT * FROM Game WHERE id = :gameId")
    Game getGameById(int gameId);

    @Query("SELECT * FROM Game")
    List<Game> getAllGames();

    @Query("SELECT * FROM Game WHERE Date(start_time) = :date")
    List<Game> getGamesOnDate(LocalDate date);

    @Query("SELECT * FROM Game WHERE table_id = :tableId ORDER BY start_time DESC")
    List<Game> getGamesPerTable(int tableId);

    @Query("SELECT * FROM Game WHERE table_id = :tableId ORDER BY start_time DESC LIMIT 1")
    Game getLastGameByTable(int tableId);

    @Query("SELECT SUM(game_value) FROM Game")
    double getTotalRaised();

    @Query("SELECT SUM(game_value) FROM Game WHERE Date(start_time) = :date")
    double getTotalRaisedOnDate(LocalDate date);

    @Query("SELECT SUM(game_value) FROM Game WHERE table_id = :tableid")
    double getTotalRaisedPerTableId(int tableid);

    @Query("SELECT SUM(game_value) FROM Game WHERE table_id = :tableid AND DATE(start_time) = :date")
    double getTotalRaisedPerTableIdOnDate(int tableid, LocalDate date);

    @Query("SELECT table_id, SUM(game_value) AS total FROM Game GROUP BY table_id")
    List<TablePayment> getTotalRaisedPerTable();

    @Query("SELECT table_id, SUM(game_value) AS total FROM Game WHERE DATE(start_time) = :date GROUP BY table_id")
    List<TablePayment> getTotalRaisedPerTableOnDate(LocalDate date);

    @Query("SELECT COUNT(DISTINCT table_id) FROM Game WHERE Date(start_time) = :date")
    int getTotalOccupiedTables(LocalDate date);
}
