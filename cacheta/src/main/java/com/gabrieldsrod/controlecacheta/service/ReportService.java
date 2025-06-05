package com.gabrieldsrod.controlecacheta.service;

import com.gabrieldsrod.controlecacheta.entities.Game;
import com.gabrieldsrod.controlecacheta.entities.Player;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportService {
    public void estabilishmentReport(List<Game> games) {
        LocalDate today = LocalDate.now();
        int gamesPlayedToday = 0;
        double raisedValueToday = 0.0;

        System.out.println("========== RELATÓRIO DIÁRIO ==========");
        for (Game g : games) {
            if (g.getStartTime().toLocalDate().equals(today)) {
                gamesPlayedToday++;
                System.out.printf("Mesa %d: %s (%s) => R$%.2f\n",
                        g.getTableNumber(),
                        g.getFormattedTime(),
                        g.getFormattedDuration(),
                        g.getTotalValue());

                raisedValueToday += g.getTotalValue();
            }
        }
        System.out.println("Partidas jogadas hoje: " + gamesPlayedToday);
        System.out.println("Valor total arrecado no hoje: R$" + String.format("%.2f", raisedValueToday));
    }

    public void playerReport (Player player, List<Game> games) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        double totalValueToPay = 0.0;

        System.out.println(dtf.format(today));

        for (Game g : games) {
            if (g.getStartTime().toLocalDate().equals(today) && g.getPlayers().contains(player)) {
                double valueToPay = 0.0;
                valueToPay += g.getTotalValue() / g.getPlayers().size();
                totalValueToPay += valueToPay;

                System.out.printf("Mesa %d: %s (%s) => R$%.2f\n",
                        g.getTableNumber(),
                        g.getFormattedTime(),
                        g.getFormattedDuration(),
                        valueToPay);
            }
        }
        System.out.println("Total a pagar: R$" + String.format("%.2f", totalValueToPay));

    }
}
