package com.gabrieldsrod.cacheta.entities.service;

import com.gabrieldsrod.cacheta.db.dao.TableDao;
import com.gabrieldsrod.cacheta.entities.Table;

import java.time.LocalDateTime;
import java.util.List;

public class TableService {
    private TableDao tableDao;

    public TableService(TableDao tableDao) {
        this.tableDao = tableDao;
    }

    public long createTable(Table table) {
        return tableDao.createTable(table);
    }

    public Table getTableById(int tableNumber) {
        Table table = tableDao.getTableById(tableNumber);
        if (table == null) {
            throw new IllegalArgumentException("Tabela com ID " + tableNumber + " não encontrada.");
        }
        return table;
    }


    public List<Table> getALlTables() {
        return tableDao.getAllTables();
    }

    public List<Table> getAvailableTables() {
        return tableDao.getTableByStatus("Livre");
    }

    public List<Table> getOccupiedTables() {
        return tableDao.getTableByStatus("Ocupada");
    }

    public LocalDateTime getTableStartTime(int id) {
        Table table = tableDao.getTableById(id);
        if (table == null) {
            throw new IllegalArgumentException("Tabela com ID " + id + " não encontrada.");
        }
        return table.getStartTime();
    }

    public void freeTable(int tableNumber) {
        tableDao.updateTableStatus(tableNumber, "Livre");
        tableDao.updateTableStartTime(tableNumber, null);
    }

    public void occupyTable(int tableNumber) {
        tableDao.updateTableStatus(tableNumber, "Ocupada");
        tableDao.updateTableStartTime(tableNumber, LocalDateTime.now());
    }

    public void deleteTable(int tableNumber) {
        tableDao.deleteTableById(tableNumber);
    }
}
