package com.gabrieldsrod.controlecacheta.program;

import com.gabrieldsrod.controlecacheta.entities.Player;
import com.gabrieldsrod.controlecacheta.entities.Table;
import com.gabrieldsrod.controlecacheta.service.GameService;
import com.gabrieldsrod.controlecacheta.service.ReportService;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        // Criar jogadores
        Player p1 = new Player(1, "Pedro");
        Player p2 = new Player(2, "Maria");
        Player p3 = new Player(3, "João");
        Player p4 = new Player(4, "Adamastor");

        // Criar mesa
        Table mesa1 = new Table();
        mesa1.addPlayer(p1);
        mesa1.addPlayer(p2);
        mesa1.addPlayer(p3);
        mesa1.addPlayer(p4);

        // Criar lista de mesas
        List<Table> mesas = new ArrayList<>();
        mesas.add(mesa1);

        // Criar serviço do jogo
        GameService gameService = new GameService(mesas); // R$15/hora

        // Iniciar a partida
        System.out.println("Iniciando partida na Mesa 1...");
        gameService.startGame(1);

        // Simular tempo de jogo
        try {
            Thread.sleep(3000); // Espera 3 segundos para simular tempo (para testar)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Finalizar partida
        System.out.println("Finalizando partida na Mesa 1...");
        gameService.endGame(1);
        System.out.println("");

        ReportService reportService = new ReportService();

        reportService.estabilishmentReport(gameService.getFinishedGames());

        System.out.println("");
        reportService.playerReport(p1, gameService.getFinishedGames());

        System.out.println("");
        reportService.playerReport(p2, gameService.getFinishedGames());

        System.out.println("");
        reportService.playerReport(p3, gameService.getFinishedGames());

        System.out.println("");
        reportService.playerReport(p4, gameService.getFinishedGames());
    }
}