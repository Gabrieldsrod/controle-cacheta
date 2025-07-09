package com.gabrieldsrod.controlecacheta.db.dao;

import com.gabrieldsrod.controlecacheta.entities.Game;

import java.time.LocalDate;
import java.util.List;

public interface GameDao {

    void createGame(Game game);
    Game getGameById(int id);
    List<Game> getAllGames();
    List<Game> getGamesOnDate(LocalDate date);
    List<Game> getGamesPerTable(int tableId);
    double getTotalRaised();
    double getTotalRaisedPerTable(int tableid);
    double getTotalRaisedOnDay(LocalDate date);

}
