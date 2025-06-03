package service;

import entities.Game;
import entities.Player;
import entities.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
            if (table.getTableNumber() == tableNumber && !table.isOccupied() ) {
                table.setOccupied(true);
                table.setStartTime(LocalDateTime.now());
                return;
            }
        }
        throw new IllegalArgumentException("Mesa não encontrada ou já está ocupada.");
    }

    public void endGame(int tableNumber) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableNumber && table.isOccupied()) {
                if(table.getStartTime() == null) {
                    throw new IllegalStateException("Mesa não iniciada corretamente, horário de início não encontrado.");
                }
                table.setEndTime(LocalDateTime.now());
                int duration = table.getGameDurationMinutes();

                double totalTableValue = table.calculateTotalTableValue(pricePerHour);
                table.setTotalTableValue(totalTableValue);

                for (Player player : table.getPlayers()) {
                    player.calculatePlayerPayments(pricePerHour, duration);
                }

                finishedGames.add(new Game(
                        table.getTableNumber(),
                        table.getStartTime(),
                        table.getEndTime(),
                        table.getGameDurationMinutes(),
                        table.getTotalTableValue()));

                table.setOccupied(false);
                return;
            }
        }
        throw new IllegalArgumentException("Mesa não encontrada ou já está livre.");
    }
}
