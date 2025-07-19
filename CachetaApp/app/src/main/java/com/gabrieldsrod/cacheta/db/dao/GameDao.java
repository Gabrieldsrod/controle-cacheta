package com.gabrieldsrod.cacheta.db.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gabrieldsrod.cacheta.entities.Game;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface GameDao {

    @Insert(onConflict = REPLACE)
    void createGame(Game game);

    @Query("SELECT * FROM Game WHERE id = :gameId")
    Game getGameById(int gameId);

    @Query("SELECT * FROM Game")
    List<Game> getAllGames();

    @Query("SELECT * FROM Game WHERE Date(start_time) = :date")
    List<Game> getGamesOnDate(LocalDate date);

    @Query("SELECT * FROM Game WHERE table_id = :tableId ORDER BY start_time DESC")
    List<Game> getGamesPerTable(int tableId);

    @Query("SELECT SUM(game_value) FROM Game")
    double getTotalRaised();

    @Query("SELECT SUM(game_value) FROM Game WHERE table_id = :tableid")
    double getTotalRaisedPerTable(int tableid);

    @Query("SELECT SUM(game_value) FROM Game WHERE Date(start_time) = :date")
    double getTotalRaisedOnDate(LocalDate date);

}
