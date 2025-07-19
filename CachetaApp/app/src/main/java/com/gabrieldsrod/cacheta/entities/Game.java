package com.gabrieldsrod.cacheta.entities;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "Game")
public class Game {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "table_id")
    private int tableId;
    @ColumnInfo(name = "start_time")
    private LocalDateTime startTime;
    @ColumnInfo(name = "end_time")
    private LocalDateTime endTime;
    @ColumnInfo(name = "duration_minutes")
    private int durationMinutes;
    @ColumnInfo(name = "game_value")
    private double gameValue;
    @Ignore
    private List<Player> players;

    public Game() {
        this.players = new ArrayList<>();
    }

    @Ignore
    public Game(int id, int tableId, LocalDateTime startTime, LocalDateTime endTime, int durationMinutes, double gameValue) {
        this.id = id;
        this.tableId = tableId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMinutes = durationMinutes;
        this.gameValue = gameValue;
        this.players = new ArrayList<>();
    }

    @Ignore
    public Game(int tableId, LocalDateTime startTime, LocalDateTime endTime, int durationMinutes, double gameValue, List<Player> players) {
        this.tableId = tableId;
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

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
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

    @SuppressLint("DefaultLocale")
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
        return id == game.id && durationMinutes == game.durationMinutes && Double.compare(gameValue, game.gameValue) == 0 && Objects.equals(tableId, game.tableId) && Objects.equals(startTime, game.startTime) && Objects.equals(endTime, game.endTime) && Objects.equals(players, game.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tableId, startTime, endTime, durationMinutes, gameValue, players);
    }

    @NonNull
    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", tableId=" + tableId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", durationMinutes=" + durationMinutes +
                ", gameValue=" + gameValue +
                ", players=" + players +
                '}';
    }
}
