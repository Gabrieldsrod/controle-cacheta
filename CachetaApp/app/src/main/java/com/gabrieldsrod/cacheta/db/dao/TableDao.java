package com.gabrieldsrod.cacheta.db.dao;

import com.gabrieldsrod.cacheta.entities.Table;

import java.time.LocalDateTime;
import java.util.List;

public interface TableDao {

    void createTable(Table table);
    void updateTableStatus(int id, String status);
    void updateTableStartTime(int id, LocalDateTime startTime);
    void deleteTableById(int id);
    Table getTableById(int id);
    List<Table> getTableByStatus(String status);
    List<Table> getAllTables();
}
