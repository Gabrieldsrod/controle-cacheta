package entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Table {
    private final Integer tableNumber;
    private Integer playerCount = 0;
    private boolean isOccupied;
    private final List<Player> players;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Table(Integer tableNumber) {
        this.tableNumber = tableNumber;
        this.isOccupied = false;
        this.players = new ArrayList<>();
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public Integer getPlayerCount() {
        return playerCount;
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

    public long getGameDurationMinutes() {
        if (startTime != null && endTime != null) {
            return Duration.between(startTime, endTime).toMinutes();
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
            playerCount = Math.max(0, playerCount - 1);
        }
    }

    public void clearPlayers() {
        players.clear();
        playerCount = 0;
    }
}
