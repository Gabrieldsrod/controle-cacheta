package entities;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private int tableNumber;
    private boolean isOccupied;
    private List<Player> players;
    // private "horario de inicio"
    // private "horario de fim"

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
    }

}
