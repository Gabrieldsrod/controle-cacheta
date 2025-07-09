package com.gabrieldsrod.controlecacheta.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    private int id;
    private Table table;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int durationMinutes;
    private double gameValue;
    private List<Player> players;

    public Game() {
        this.players = new ArrayList<>();
    }

    public Game(int id, Table table, LocalDateTime startTime, LocalDateTime endTime, int durationMinutes, double gameValue) {
        this.id = id;
        this.table = table;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMinutes = durationMinutes;
        this.gameValue = gameValue;
        this.players = new ArrayList<>();
    }

    public Game(Table table, LocalDateTime startTime, LocalDateTime endTime, int durationMinutes, double gameValue, List<Player> players) {
        this.table = table;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMinutes = durationMinutes;
        this.gameValue = gameValue;
        this.players = players;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && durationMinutes == game.durationMinutes && Double.compare(gameValue, game.gameValue) == 0 && Objects.equals(table, game.table) && Objects.equals(startTime, game.startTime) && Objects.equals(endTime, game.endTime) && Objects.equals(players, game.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, table, startTime, endTime, durationMinutes, gameValue, players);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", table=" + table +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", durationMinutes=" + durationMinutes +
                ", gameValue=" + gameValue +
                ", players=" + players +
                '}';
    }
}
