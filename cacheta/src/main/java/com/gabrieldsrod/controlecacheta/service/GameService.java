package com.gabrieldsrod.controlecacheta.service;

import com.gabrieldsrod.controlecacheta.entities.Game;
import com.gabrieldsrod.controlecacheta.entities.Player;
import com.gabrieldsrod.controlecacheta.entities.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameService {
    private List<Table> tables;
    private final List<Game> finishedGames = new ArrayList<>();
    private double pricePerHour = 15.00;

    public GameService(List<Table> tables) {
        this.tables = tables;
    }

    public GameService(List<Table> tables, double pricePerHour) {
        this.tables = tables;
        this.pricePerHour = pricePerHour;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<Game> getFinishedGames() {
        return finishedGames;
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
                table.setStatus("ocupada");
                table.setStartTime(LocalDateTime.now());
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
                        table,
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

                finishedGames.add(game);

                table.setStatus("Livre");
                table.clearPlayers();
                table.setStartTime(null);

                return;
            }
        }
        throw new IllegalArgumentException("Mesa não encontrada ou já está livre.");
    }

}
