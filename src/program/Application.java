package program;

import entities.Table;
import entities.Player;

import java.util.List;
import java.util.ArrayList;

public class Application {
    private List<Table> tables;
    private double pricePerHour = 15.00;

    public Application(double pricePerHour) {
        this.tables = new ArrayList<>();
        this.pricePerHour = pricePerHour;
    }

    public void addTable(int tableNumber) {
        tables.add(new Table(tableNumber));
    }

    public void removeTable(int tableNumber) {
        tables.removeIf(table -> table.getTableNumber() == tableNumber);
    }

    public Table getTable(int tableNumber) {
        for (Table table : tables) {
            if (table.getTableNumber() == tableNumber) {
                return table;
            }
        }
        return null;
    }

}
