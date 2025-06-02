package service;

import entities.Player;
import entities.Table;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class GameService {
    private List<Table> tables;
    private double pricePerHour = 15.00;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

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

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void startGame(int tableNumber) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableNumber && !table.isOccupied() ) {
                this.startTime = LocalDateTime.now();
                table.setOccupied(true);
            }
        }
        throw new IllegalArgumentException("Table not found or already occupied.");
    }

    public void endGame(int tableNumber) {
        if (startTime != null) {
            this.endTime = LocalDateTime.now();
            for (Table table : tables) {
                if (table.getTableNumber() == tableNumber && table.isOccupied()) {
                    table.setOccupied(false);

                    for (Player player : table.getPlayers()) {
                        player.calculatePlayerPayments(pricePerHour, getGameDurationMinutes());
                    }
                }
            }
        } else {
            throw new IllegalStateException("A partida ainda não foi iniciada.");
        }
    }

    public long getGameDurationMinutes() {
        if (startTime != null && endTime != null) {
            return Duration.between(startTime, endTime).toMinutes();
        } else {
            throw new IllegalStateException("A partida ainda não foi finalizada.");
        }
    }
}
