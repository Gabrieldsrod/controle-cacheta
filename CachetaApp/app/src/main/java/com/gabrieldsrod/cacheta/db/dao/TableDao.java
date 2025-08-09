package com.gabrieldsrod.cacheta.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.gabrieldsrod.cacheta.entities.Table;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface TableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long createTable(Table table);

    @Query("UPDATE `Table` SET status = :status WHERE tableNumber = :id")
    void updateTableStatus(int id, String status);

    @Query("UPDATE `Table` SET start_time = :startTime WHERE tableNumber = :id")
    void updateTableStartTime(int id, LocalDateTime startTime);

    @Delete
    void deleteTable(Table table);

    @Query("SELECT * FROM `Table` WHERE tableNumber = :id")
    Table getTableById(int id);

    @Query("SELECT * FROM `Table` WHERE status = :status")
    List<Table> getTableByStatus(String status);

    @Query("SELECT * FROM `Table`")
    List<Table> getAllTables();
}
