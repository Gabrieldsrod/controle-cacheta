package com.gabrieldsrod.cacheta.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "Table")
public class Table {
    @PrimaryKey(autoGenerate = true)
    private int tableNumber;
    @ColumnInfo(name = "status")
    private String status = "Livre";
    @ColumnInfo(name = "start_time")
    private LocalDateTime startTime;
    @Ignore
    private int playerCount = 0;
    @Ignore
    private List<Player> players;

    public Table() {
        this.players = new ArrayList<>();
    }

    @Ignore
    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.status = "Livre";
        this.players = new ArrayList<>();
        this.playerCount = 0;
    }

    @Ignore
    public Table(int tableNumber, int playerCount, double tableValue, String status, LocalDateTime startTime) {
        this.tableNumber = tableNumber;
        this.playerCount = playerCount;
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return tableNumber == table.tableNumber && playerCount == table.playerCount && Objects.equals(status, table.status) && Objects.equals(players, table.players) && Objects.equals(startTime, table.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableNumber, playerCount, status, players, startTime);
    }

    @NonNull
    @Override
    public String toString() {
        return "Table{" +
                "tableNumber=" + tableNumber +
                ", playerCount=" + playerCount +
                ", status='" + status + '\'' +
                ", players=" + players +
                ", startTime=" + startTime +
                '}';
    }
}
