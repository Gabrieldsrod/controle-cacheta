package com.gabrieldsrod.cacheta.service;

import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.entities.Table;
import com.gabrieldsrod.cacheta.entities.service.GamePlayerService;
import com.gabrieldsrod.cacheta.entities.service.GameService;
import com.gabrieldsrod.cacheta.entities.service.TableService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MatchService {
    private List<Table> tables;
    private double pricePerHour = 10.0;
    private final TableService tableService;
    private final GameService gameService;
    private final GamePlayerService gamePlayerService;

    public MatchService(TableService tableService, GameService gameService, GamePlayerService gamePlayerService) {
        this.tables = new ArrayList<>();
        this.tableService = tableService;
        this.gameService = gameService;
        this.gamePlayerService = gamePlayerService;
    }

    public void loadTables() {
        this.tables = tableService.getAllTables();
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public void startGame(Table table) {
        loadTables();

        if (table.getStatus().equals("Livre")) {
            LocalDateTime startTime = LocalDateTime.now();
            String status = "Ocupada";
            table.setStatus(status);
            table.setStartTime(startTime);
            tableService.occupyTable(table.getTableNumber());

            Game game = new Game(
                    table.getTableNumber(),
                    table.getStartTime(),
                    null,
                    0,
                    0.0 // valor temporário, será calculado depois
            );

            long gameId = gameService.createGame(game);
            game.setId((int) gameId);

            gamePlayerService.insertParticipants(game.getId(), table.getPlayers());

            return;

        }
        throw new IllegalArgumentException("Mesa não encontrada ou já está ocupada.");
    }

    public void endGame(Table table) {
        loadTables();

        if (table.getStatus().equals("Ocupada")) {
            if (table.getStartTime() == null) {
                throw new IllegalStateException("Mesa não iniciada corretamente.");
            }

            LocalDateTime endTime = LocalDateTime.now();
            int duration = table.getDurationMinutes(endTime);

            Game game = gameService.getLastGameByTable(table.getTableNumber());

            game.setEndTime(endTime);
            game.setDurationMinutes(duration);
            double valorTotal = game.calculateGameValue(pricePerHour);
            game.setGameValue(valorTotal);

            gameService.updateGame(game);

            table.setStatus("Livre");
            table.clearPlayers();
            table.setStartTime(null);
            tableService.freeTable(table.getTableNumber());

            System.out.println("Partida encerrada na mesa " + table.getTableNumber() + " com sucesso.");
            return;
        }

        throw new IllegalArgumentException("Mesa não encontrada ou já está livre.");
    }
}
