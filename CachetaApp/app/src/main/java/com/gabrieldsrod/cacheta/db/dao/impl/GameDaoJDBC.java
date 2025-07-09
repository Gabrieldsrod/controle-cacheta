package com.gabrieldsrod.cacheta.db.dao.impl;

import com.gabrieldsrod.cacheta.db.Database;
import com.gabrieldsrod.cacheta.db.DatabaseException;
import com.gabrieldsrod.cacheta.db.dao.GameDao;
import com.gabrieldsrod.cacheta.entities.EntityInstantiator;
import com.gabrieldsrod.cacheta.entities.Game;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameDaoJDBC implements GameDao {

    private Connection conn;

    public GameDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void createGame(Game game) {
        PreparedStatement st = null;
        String sql = "INSERT INTO Game (table_id, start_time, end_time, duration_minutes, total_value) VALUES (?, ?," +
                " ?, ?, ?)";

        try {
            st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            st.setInt(1, game.getTable().getTableNumber());
            st.setString(2, game.getStartTime().toString());
            st.setString(3, game.getEndTime().toString());
            st.setInt(4, game.getDurationMinutes());
            st.setDouble(5, game.getGameValue());
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rsGame = st.getGeneratedKeys();
                if (rsGame.next()) {
                    int id = rsGame.getInt(1);
                    game.setId(id);
                }
                Database.closeResultSet(rsGame);
            } else {
                throw new DatabaseException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            Database.closeStatement(st);
        }
    }

    @Override
    public Game getGameById(int id) {
        PreparedStatement st = null;
        ResultSet rsGame = null;
        String sql = "SELECT * FROM Game WHERE game_id = ?";
        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, id);
            rsGame = st.executeQuery();
            if (rsGame.next()) {
                Game game = EntityInstantiator.instantiateGame(rsGame);
                return game;
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(st);
            Database.closeResultSet(rsGame);
        }
    }

    @Override
    public List<Game> getAllGames() {
        PreparedStatement st = null;
        ResultSet rsGame = null;
        List<Game> games = new ArrayList<>();

        String sql = "SELECT * FROM Game";
        try {
            st = conn.prepareStatement(sql);
            rsGame = st.executeQuery();

            while(rsGame.next()) {
                Game game = EntityInstantiator.instantiateGame(rsGame);
                games.add(game);
            }
            return games;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeResultSet(rsGame);
            Database.closeStatement(st);

        }
    }

    @Override
    public List<Game> getGamesOnDate(LocalDate date) {
        PreparedStatement st = null;
        ResultSet rsGame = null;
        List<Game> games = new ArrayList<>();

        String sql = "SELECT * FROM Game WHERE Date(start_time) = ?";
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, date.toString());
            rsGame = st.executeQuery();

            while(rsGame.next()) {
                Game game = EntityInstantiator.instantiateGame(rsGame);
                games.add(game);
            }
            return games;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(st);
            Database.closeResultSet(rsGame);
        }
    }

    @Override
    public List<Game> getGamesPerTable(int tableId) {
        PreparedStatement st = null;
        ResultSet rsGame = null;
        List<Game> games = new ArrayList<>();

        String sql = "SELECT * FROM Game WHERE table_id = ? ORDER BY start_time DESC";
        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, tableId);
            rsGame = st.executeQuery();

            while(rsGame.next()) {
                Game game = EntityInstantiator.instantiateGame(rsGame);
                games.add(game);
            }
            return games;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(st);
            Database.closeResultSet(rsGame);

        }
    }

    @Override
    public double getTotalRaised() {
        PreparedStatement st = null;
        ResultSet rsRaised = null;

        String sql = "SELECT SUM(total_value) FROM Game";
        try {
            st = conn.prepareStatement(sql);
            rsRaised = st.executeQuery();

            if(rsRaised.next()) {
                double totalRaised = rsRaised.getDouble(1);
                return totalRaised;
            }
            return 0.0;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeResultSet(rsRaised);
            Database.closeStatement(st);
        }

    }

    @Override
    public double getTotalRaisedPerTable(int tableId) {
        PreparedStatement st = null;
        ResultSet rsRaised = null;

        String sql = "SELECT SUM(total_value) FROM Game WHERE table_id = ?";
        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, tableId);
            rsRaised = st.executeQuery();

            if(rsRaised.next()) {
                double totalRaised = rsRaised.getDouble(1);
                return totalRaised;
            }
            return 0.0;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(st);
            Database.closeResultSet(rsRaised);
        }

    }

    @Override
    public double getTotalRaisedOnDay(LocalDate date) {
        PreparedStatement st = null;
        ResultSet rsRaised = null;

        String sql = "SELECT SUM(total_value) FROM Game WHERE Date(start_time) = ?";
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, date.toString());
            rsRaised = st.executeQuery();

            if(rsRaised.next()){
                double totalRaised = rsRaised.getDouble(1);
                return totalRaised;
            }
            return 0.0;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeResultSet(rsRaised);
            Database.closeStatement(st);
        }
    }
}
