package entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private final int tableNumber;
    private int playerCount = 0;
    private double tableValue = 0.0;
    private boolean isOccupied;
    private final List<Player> players;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.isOccupied = false;
        this.players = new ArrayList<>();
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public double getTableValue() {
        return tableValue;
    }

    public void setTableValue(double tableValue) {
        this.tableValue = tableValue;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }

    public List<Player> getPlayers() {
        return players;
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

    public int getGameDurationMinutes() {
        if (startTime != null && endTime != null) {
            long duration = Duration.between(startTime, endTime).toMinutes();
            return (int) duration;
        } else {
            return 0;
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
        playerCount++;
    }

    public void removePlayer(Player player) {
        if (players.remove(player)) {
            playerCount = Math.max(0, playerCount - 1);  // Evitar numeros negativos em playerCount
        }
    }

    public void clearPlayers() {
        players.clear();
        playerCount = 0;
    }

    public double calculateTableValue(double pricePerHour) {
        int duracao = getGameDurationMinutes();
        int horas = Math.max(1, (int) Math.ceil(duracao / 60.0));
        return pricePerHour * horas * getPlayerCount();
    }
}
