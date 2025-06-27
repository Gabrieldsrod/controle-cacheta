package com.gabrieldsrod.controlecacheta.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Game {
    private Table table;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int durationMinutes;
    private double gameValue;
    private List<Player> players;

    public Game(Table table, LocalDateTime startTime, LocalDateTime endTime, int durationMinutes, double totalValue,
                List<Player> players) {
        this.table = table;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMinutes = durationMinutes;
        this.gameValue = totalValue;
        this.players = players;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setGameValue(double totalValue) {
        this.gameValue = totalValue;
    }

    public double getGameValue() {
        return gameValue;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }


    public double calculateGameValue(double pricePerHour) {
        int hours = Math.max(1, (int) Math.ceil(durationMinutes / 60.0));
        return pricePerHour * hours * players.size();
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
