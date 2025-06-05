package com.gabrieldsrod.controlecacheta.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Game {
    private final int tableNumber;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int durationMinutes;
    private final double totalValue;
    private List<Player> players;

    public Game(int tableNumber, LocalDateTime startTime, LocalDateTime endTime, int durationMinutes, double totalValue, List<Player> players) {
        this.tableNumber = tableNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMinutes = durationMinutes;
        this.totalValue = totalValue;
        this.players = players;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getFormattedDuration() {
        int hours = durationMinutes / 60;
        int minutes = durationMinutes % 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    public String getFormattedTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        return dtf.format(startTime.toLocalTime())  + " - " + dtf.format(endTime.toLocalTime());
    }
}
