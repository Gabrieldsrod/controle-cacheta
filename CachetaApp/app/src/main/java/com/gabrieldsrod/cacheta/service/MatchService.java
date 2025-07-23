package com.gabrieldsrod.cacheta.service;

import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.entities.Player;
import com.gabrieldsrod.cacheta.entities.Table;
import com.gabrieldsrod.cacheta.entities.service.GamePlayerService;
import com.gabrieldsrod.cacheta.entities.service.GameService;
import com.gabrieldsrod.cacheta.entities.service.TableService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MatchService {
    private List<Table> tables;
    private double pricePerHour = 15.00;

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
        this.tables = tableService.getALlTables();
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
    
    public void startGame(int tableNumber) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableNumber && table.getStatus().equals("Livre")) {
                LocalDateTime startTime = LocalDateTime.now();
                String status = "Ocupada";
                table.setStatus(status);
                table.setStartTime(startTime);

                tableService.occupyTable(tableNumber);

                return;
            }
        }
        throw new IllegalArgumentException("Mesa não encontrada ou já está ocupada.");
    }

    public void endGame(int tableNumber) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableNumber && table.getStatus().equals("Ocupada")) {
                if (table.getStartTime() == null) {
                    throw new IllegalStateException("Mesa não iniciada corretamente.");
                }

                LocalDateTime endTime = LocalDateTime.now();
                int duration = table.getDurationMinutes(endTime);

                Game game = new Game(
                        tableNumber,
                        table.getStartTime(),
                        endTime,
                        duration,
                        0.0, // valor temporário, será calculado depois
                        new ArrayList<>(table.getPlayers())
                );

                double valorTotal = game.calculateGameValue(pricePerHour);
                game.setGameValue(valorTotal);

                for (Player player : table.getPlayers()) {
                    player.calculatePlayerPayments(pricePerHour, duration);
                }

                gameService.createGame(game);
                gamePlayerService.insertParticipants(game.getId(), game.getPlayers());

                tableService.freeTable(tableNumber);

                table.setStatus("Livre");
                table.clearPlayers();
                table.setStartTime(null);

                System.out.println("Partida encerrada na mesa " + tableNumber + " com sucesso.");
                return;
            }
        }
        throw new IllegalArgumentException("Mesa não encontrada ou já está livre.");
    }
}
