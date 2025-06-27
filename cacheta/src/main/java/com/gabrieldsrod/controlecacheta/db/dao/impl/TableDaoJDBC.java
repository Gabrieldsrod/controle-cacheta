package com.gabrieldsrod.controlecacheta.db.dao.impl;

import com.gabrieldsrod.controlecacheta.db.dao.TableDao;
import com.gabrieldsrod.controlecacheta.entities.Table;

import java.sql.Connection;
import java.util.List;

public class TableDaoJDBC implements TableDao {

    private Connection conn;

    public TableDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void createTable(Table table) {

    }

    @Override
    public void updateTableStatus(int id, String status) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public Table getById(int id) {
        return null;
    }

    @Override
    public List<Table> getAllTables() {
        return List.of();
    }
}
