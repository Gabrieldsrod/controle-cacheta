package com.gabrieldsrod.cacheta.service;

import com.gabrieldsrod.cacheta.dto.EstablishmentSummary;
import com.gabrieldsrod.cacheta.dto.MatchSummary;
import com.gabrieldsrod.cacheta.dto.PlayerSummary;
import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.entities.Player;
import com.gabrieldsrod.cacheta.entities.service.GamePlayerService;
import com.gabrieldsrod.cacheta.entities.service.GameService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportService {
    private final GameService gameService;
    private final GamePlayerService gamePlayerService;

    public ReportService(GameService gameService, GamePlayerService gamePlayerService) {
        this.gameService = gameService;
        this.gamePlayerService = gamePlayerService;
    }

    public List<Game> loadGames() {
        return gameService.getFinishedGames();
    }

    public EstablishmentSummary EstablishmentReport() {
        List<Game> games = gameService.getTodayGames();
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));  // today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")

        return new EstablishmentSummary(
                gameService.getTotalOccupiedTablesToday(),
                gameService.getTotalRaisedToday(),
                today
        );
    }

    public PlayerSummary playerReport(Player player) {
        List<Game> gamesToday = gamePlayerService.getGamesPerPlayerToday(player.getId());
        List<MatchSummary> matchList = new ArrayList<>();
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        for (Game g : gamesToday) {
            double valueToPay = g.getGameValue() / 4;

            matchList.add(new MatchSummary(
                    g.getTableId(),
                    g.getFormattedTime(),
                    g.getFormattedDuration(),
                    valueToPay
            ));
        }

        return new PlayerSummary(
                player.getName(),
                today,
                matchList,
                gamePlayerService.getTotalPaidByPlayerToday(player.getId())
        );
    }
}