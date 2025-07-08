package com.gabrieldsrod.controlecacheta.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private int tableNumber;
    private int playerCount = 0;
    private double tableValue = 0.0;
    private String status;
    private List<Player> players;
    private LocalDateTime startTime;

    public Table() {
        this.players = new ArrayList<>();
    }

    public Table(int tableNumber, String status) {
        this.tableNumber = tableNumber;
        this.status = status;
        this.players = new ArrayList<>();
    }

    public Table(int tableNumber, int playerCount, double tableValue, String status, LocalDateTime startTime) {
        this.tableNumber = tableNumber;
        this.playerCount = playerCount;
        this.tableValue = tableValue;
        this.status = status;
        this.startTime = startTime;
        this.players = new ArrayList<>();
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public double getTableValue() {
        return tableValue;
    }

    public void setTableValue(double tableValue) {
        this.tableValue = tableValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
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

    public int getDurationMinutes(LocalDateTime endTime) {
        if (startTime != null && endTime != null) {
            long duration = Duration.between(startTime, endTime).toMinutes();
            return (int) duration;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableNumber=" + tableNumber +
                ", playerCount=" + playerCount +
                ", tableValue=" + tableValue +
                ", status='" + status + '\'' +
                ", players=" + players +
                ", startTime=" + startTime +
                '}';
    }
}
