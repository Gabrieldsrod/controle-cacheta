package entities;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final int tableNumber;
    private int playerCount = 0;
    private boolean isOccupied;
    private final List<Player> players;

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

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
        playerCount++;
    }

    public void removePlayer(Player player) {
        players.remove(player);
        playerCount--;
    }

    public void clearPlayers() {
        players.clear();
    }


}
