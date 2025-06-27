package com.gabrieldsrod.controlecacheta.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private int tableNumber;
    private int playerCount = 0;
    private double tableValue = 0.0;
    private boolean isOccupied;
    private List<Player> players;
    private LocalDateTime startTime;

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.isOccupied = false;
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

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
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
}
