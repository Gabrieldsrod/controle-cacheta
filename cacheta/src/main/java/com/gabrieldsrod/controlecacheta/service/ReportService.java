package main.java.com.gabrieldsrod.controlecacheta.service;

import main.java.com.gabrieldsrod.controlecacheta.entities.Game;

import java.time.LocalDate;
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
}
