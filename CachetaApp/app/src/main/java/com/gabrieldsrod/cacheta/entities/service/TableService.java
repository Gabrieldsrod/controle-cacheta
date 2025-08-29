package com.gabrieldsrod.cacheta.entities.service;

import com.gabrieldsrod.cacheta.db.dao.TableDao;
import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.entities.Table;

import java.time.LocalDateTime;
import java.util.List;

public class TableService {
    private final TableDao tableDao;
    private final GameService gameService;

    public TableService(TableDao tableDao, GameService gameService) {
        this.tableDao = tableDao;
        this.gameService = gameService;
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


    public List<Table> getAllTables() {
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

    public boolean removeTable(int tableNumber) {
        Table table = tableDao.getTableById(tableNumber);

        if (table == null) {
            throw new IllegalArgumentException("Mesa não encontrada.");
        }

        if (!"Livre".equalsIgnoreCase(table.getStatus())) {
            throw new IllegalStateException("A mesa está ocupada e não pode ser removida.");
        }

        List<Game> games = gameService.getGamesPerTable(tableNumber);
        if (!games.isEmpty()) {
            throw new IllegalStateException("A mesa possui partidas registradas e não pode ser removida.");
        }

        tableDao.deleteTable(table);
        return true;
    }
}
