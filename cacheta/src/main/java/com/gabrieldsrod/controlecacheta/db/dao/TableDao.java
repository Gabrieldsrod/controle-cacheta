package com.gabrieldsrod.controlecacheta.db.dao;

import com.gabrieldsrod.controlecacheta.entities.Table;

import java.util.List;

public interface TableDao {

    void createTable(Table table);
    void updateTableStatus(int id, String status);
    void deleteById(int id);
    Table getById(int id);
    List<Table> getByStatus(String status);
    List<Table> getAllTables();
}
