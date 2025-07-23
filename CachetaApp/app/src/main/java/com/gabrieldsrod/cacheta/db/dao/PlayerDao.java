package com.gabrieldsrod.cacheta.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.gabrieldsrod.cacheta.entities.Player;

import java.util.List;

@Dao
public interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long createPlayer(Player player);

    @Query("SELECT * FROM Player WHERE id = :id")
    Player getPlayerById(int id);

    @Query("SELECT * FROM Player WHERE name = :name;")
    Player getPlayerByName(String name);

    @Query("SELECT * FROM Player")
    List<Player> getAllPlayers();

    @Query("UPDATE Player SET name = :name WHERE id = :id")
    void updatePlayer(int id, String name);

    @Query("DELETE FROM Player WHERE id = :id")
    void deletePlayerById(int id);
}
