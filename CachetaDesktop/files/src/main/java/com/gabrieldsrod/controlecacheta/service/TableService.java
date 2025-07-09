package com.gabrieldsrod.controlecacheta.service;

import com.gabrieldsrod.controlecacheta.db.dao.TableDao;
import com.gabrieldsrod.controlecacheta.entities.Table;

import java.util.List;

public class TableService {
    private TableDao tableDao;

    public TableService(TableDao tableDao) {
        this.tableDao = tableDao;
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

    public void freeTable(int tableNumber) {
        tableDao.updateTableStatus(tableNumber, "Livre");
        tableDao.updateTableStartTime(tableNumber, null);
    }
}
