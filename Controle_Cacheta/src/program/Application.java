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

        app.addTable(1);
        Table table = app.getTable(1);

        Player player1 = new Player("João");
        Player player2 = new Player("Maria");
        table.addPlayer(player1);
        table.addPlayer(player2);

        System.out.println("Iniciando a partida...");
        table.startGame();

        // Partida em andamento - não sei ainda
        // Ponto a ver: como simular o tempo de jogo?

        System.out.println("Finalizando a partida...");
        table.endGame();

        table.calculatePlayerPayments(app.pricePerHour);

        for (Player player : table.getPlayers()) {
            System.out.println("Jogador: " + player.getName());
            System.out.println("Tempo total jogado: " + player.getTotalTimeMinutes() + " minutos");
            System.out.println("Valor total a pagar: R$ " + String.format("%.2f", player.getTotalValueToPay()));
        }
    }
}
