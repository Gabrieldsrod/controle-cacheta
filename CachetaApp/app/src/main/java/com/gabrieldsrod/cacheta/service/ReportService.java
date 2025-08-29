package com.gabrieldsrod.cacheta.service;

import com.gabrieldsrod.cacheta.dto.EstablishmentSummary;
import com.gabrieldsrod.cacheta.dto.MatchSummary;
import com.gabrieldsrod.cacheta.dto.PlayerSummary;
import com.gabrieldsrod.cacheta.dto.TableSummary;
import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.entities.Player;
import com.gabrieldsrod.cacheta.entities.Table;
import com.gabrieldsrod.cacheta.entities.service.GamePlayerService;
import com.gabrieldsrod.cacheta.entities.service.GameService;
import com.gabrieldsrod.cacheta.entities.service.PlayerService;
import com.gabrieldsrod.cacheta.entities.service.TableService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {
    private final PlayerService playerService;
    private final GameService gameService;
    private final GamePlayerService gamePlayerService;
    private final TableService tableService;

    public ReportService(PlayerService playerService, GameService gameService, GamePlayerService gamePlayerService, TableService tableService) {
        this.playerService = playerService;
        this.gameService = gameService;
        this.gamePlayerService = gamePlayerService;
        this.tableService = tableService;
    }

    public List<Game> loadGames() {
        return gameService.getFinishedGames();
    }

    public EstablishmentSummary EstablishmentReport() {
        List<Table> tables = tableService.getAllTables();
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        List<TableSummary> tableSummaries = new ArrayList<>();

        for (Table table : tables) {
            Integer topId = gamePlayerService.getTopPlayerIdToday(table.getTableNumber());
            Player top = (topId == null) ? null : playerService.getPlayerById(topId);
            int minutes = (top == null) ? 0 : gamePlayerService.getTopPlayerMinutesOnDate(
                    table.getTableNumber(), top.getId(), LocalDate.now()
            );

            double totalRaisedPerTable = gameService.getTotalRaisedPerTableToday(table.getTableNumber());

            tableSummaries.add(new TableSummary(
                    table.getTableNumber(),
                    totalRaisedPerTable,
                    top,            // pode ser null
                    minutes         // 0 quando top == null
            ));
        }

        return new EstablishmentSummary(
                gameService.getTotalOccupiedTablesToday(),
                gameService.getTotalRaisedToday(),
                today,
                tableSummaries
        );
    }

    public PlayerSummary playerReport(Player player) {
        List<Game> gamesToday = gamePlayerService.getGamesPerPlayerToday(player.getId())
                                                                        .stream()
                                                                        .distinct()
                                                                        .collect(Collectors.toList());

        if (gamesToday.isEmpty()) {
            return null;
        }
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