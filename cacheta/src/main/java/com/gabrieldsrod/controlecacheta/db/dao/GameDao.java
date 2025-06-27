package com.gabrieldsrod.controlecacheta.db.dao;

import com.gabrieldsrod.controlecacheta.entities.Game;

import java.time.LocalDate;
import java.util.List;

public interface GameDao {

    int createGame(Game game);
    Game getGameById(int id);
    List<Game> getAllGames();
    List<Game> getGamesOnDate(LocalDate date);
    List<Game> getGamesOnDate(LocalDate startDate, LocalDate endDate);
    List<Game> getGamesPerTable(int tableId);
    double getTotalRaised();
    double getTotalRaisedPerTable(int id);
    double getTotalRaisedOnDay(int id);

}
