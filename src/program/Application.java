package program;

import entities.Table;
import entities.Player;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

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

        Scanner scanner = new Scanner(System.in);

        app.addTable(1);
        Table table = app.getTable(1);

        Player player1 = new Player("João");
        Player player2 = new Player("Maria");
        table.addPlayer(player1);
        table.addPlayer(player2);

        System.out.println("Digite o horário de início da partida (formato: dd-MM-yyyy HH:mm):");
        String startTimeInput = scanner.nextLine();
        try {
            table.startGame(startTimeInput);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data/hora inválido. Tente novamente.");
            scanner.close();
            return;
        }

        System.out.println("Partida em andamento...");

        System.out.println("Digite o horário de término da partida (formato: dd-MM-yyyy HH:mm):");
        String endTimeInput = scanner.nextLine();
        try {
            table.endGame(endTimeInput);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data/hora inválido. Tente novamente.");
            scanner.close();
            return;
        }

        table.calculatePlayerPayments(app.pricePerHour);

        for (Player player : table.getPlayers()) {
            System.out.println("Jogador: " + player.getName());
            System.out.println("Tempo total jogado: " + player.getTotalTimeMinutes() + " minutos");
            System.out.println("Valor total a pagar: R$ " + String.format("%.2f", player.getTotalValueToPay()));
        }

        scanner.close();
    }
}
