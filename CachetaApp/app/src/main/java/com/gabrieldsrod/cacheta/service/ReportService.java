package com.gabrieldsrod.cacheta.service;

import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.entities.Player;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportService {

    public void establishmentReport(List<Game> games) {
        LocalDate today = LocalDate.now();
        int gamesPlayedToday = 0;
        double raisedValueToday = 0.0;

        System.out.println("========== RELATÓRIO DIÁRIO ==========");
        System.out.println("Data: " + today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println();

        for (Game g : games) {
            if (g.getStartTime().toLocalDate().equals(today)) {
                gamesPlayedToday++;
                raisedValueToday += g.getGameValue();

                System.out.printf(
                        "Mesa %d: %s (%s) => R$ %.2f%n",
                        g.getTable().getTableNumber(),
                        g.getFormattedTime(),
                        g.getFormattedDuration(),
                        g.getGameValue()
                );
            }
        }

        System.out.println();
        System.out.println("Partidas jogadas hoje: " + gamesPlayedToday);
        System.out.println("Valor total arrecadado hoje: R$ " + String.format("%.2f", raisedValueToday));
        System.out.println("======================================");
        System.out.println();
    }

    /**
     * Gera relatório individual do jogador:
     * - Lista de partidas do jogador no dia
     * - Valor individual a pagar
     */
    public void playerReport(Player player, List<Game> games) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        double totalValueToPay = 0.0;

        System.out.println("========== EXTRATO DO JOGADOR ==========");
        System.out.println("Data: " + dtf.format(today));
        System.out.println("Jogador: " + player.getName());
        System.out.println();

        for (Game g : games) {
            if (g.getStartTime().toLocalDate().equals(today) && g.getPlayers().contains(player)) {
                double valueToPay = g.getGameValue() / g.getPlayers().size();
                totalValueToPay += valueToPay;

                System.out.printf(
                        "Mesa %d: %s (%s) => R$ %.2f%n",
                        g.getTable().getTableNumber(),
                        g.getFormattedTime(),
                        g.getFormattedDuration(),
                        valueToPay
                );
            }
        }

        System.out.println();
        System.out.println("Total a pagar hoje: R$ " + String.format("%.2f", totalValueToPay));
        System.out.println("=========================================");
        System.out.println();
    }
}