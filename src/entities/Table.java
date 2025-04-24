package entities;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Duration;

public class Table {
    private int tableNumber;
    private boolean isOccupied;
    private List<Player> players;
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

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void clearPlayers() {
        players.clear();
    }

    public void reset() {
        this.isOccupied = false;
        this.players.clear();
        this.startTime = null;
        this.endTime = null;
    }

    public void startGame() {
        if (!isOccupied) {
            this.startTime = LocalDateTime.now();
            this.isOccupied = true;
        } else {
            throw new IllegalStateException("A mesa já está ocupada.");
        }
    }

    public void endGame() {
        if (isOccupied) {
            this.endTime = LocalDateTime.now();
            this.isOccupied = false;
        } else {
            throw new IllegalStateException("A mesa não está ocupada.");
        }
    }

    public long getGameDurationMinutes() {
        if (startTime != null && endTime != null) {
            return Duration.between(startTime, endTime).toMinutes();
        } else {
            throw new IllegalStateException("A partida ainda não foi finalizada.");
        }
    }

    public void calculatePlayerPayments(double pricePerHour) {
        if (startTime != null && endTime != null) {
            long durationMinutes = getGameDurationMinutes();
            double pricePerMinute = pricePerHour / 60;
            double totalPrice = durationMinutes * pricePerMinute;

            for (Player player : players) {
                player.addTime((int) durationMinutes);
                player.addValue(totalPrice / players.size());
            }
        } else {
            throw new IllegalStateException("A partida ainda não foi finalizada.");
        }
    }
}
