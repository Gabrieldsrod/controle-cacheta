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

    public static void main(String[] args) {
        Application app = new Application(15.00);

        // Adicionando uma mesa
        app.addTable(1);
        Table table = app.getTable(1);

        // Iniciando uma partida
        System.out.println("Iniciando a partida...");
        table.startGame();

        // Simulando tempo de jogo (aguarde alguns segundos ou use Thread.sleep para simular)
        try {
            Thread.sleep(5000); // Aguarda 5 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Finalizando a partida
        System.out.println("Finalizando a partida...");
        table.endGame();

        // Calculando a duração
        long durationMinutes = table.getGameDurationMinutes();
        System.out.println("Duração da partida: " + durationMinutes + " minutos");

        // Calculando o valor a pagar
        double pricePerMinute = app.pricePerHour / 60;
        double totalPrice = durationMinutes * pricePerMinute;
        System.out.println("Valor total a pagar: R$ " + String.format("%.2f", totalPrice));
    }
}
