package com.gabrieldsrod.controlecacheta.db.dao.impl;

import com.gabrieldsrod.controlecacheta.db.Database;
import com.gabrieldsrod.controlecacheta.db.DatabaseException;
import com.gabrieldsrod.controlecacheta.db.dao.GamePlayerDao;
import com.gabrieldsrod.controlecacheta.entities.EntityInstantiator;
import com.gabrieldsrod.controlecacheta.entities.Game;
import com.gabrieldsrod.controlecacheta.entities.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GamePlayerDaoJDBC implements GamePlayerDao {

    private Connection conn;

    public GamePlayerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertParticipants(int gameId, List<Player> players) {
        PreparedStatement st = null;
        String sql = "INSERT INTO GamePlayer (game_id, player_id) VALUES (?, ?)";

        try {
            st = conn.prepareStatement(sql);
            for (Player p : players) {
                st.setInt(1, gameId);
                st.setInt(2, p.getId());
                st.addBatch();
            }
            st.executeBatch();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(st);
        }
    }

    @Override
    public List<Player> getPlayersPerGame(int gameId) {
        PreparedStatement st = null;
        ResultSet rsPlayers = null;
        List<Player> players = new ArrayList<>();

        String sql = "SELECT p.* " +
                "FROM GamePlayer gp " +
                "JOIN Player p ON gp.player_id = p.player_id " +
                "WHERE gp.game_id = ?";
        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, gameId);

            rsPlayers = st.executeQuery();
            while(rsPlayers.next()) {
                Player player = EntityInstantiator.instantiatePlayer(rsPlayers);
                players.add(player);
            }
            return players;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(st);
            Database.closeResultSet(rsPlayers);
        }
    }

    @Override
    public List<Game> getGamesPerPlayer(int playerId) {
        PreparedStatement st = null;
        ResultSet rsGames = null;
        List<Game> games = new ArrayList<>();

        String sql = "SELECT g.* " +
                "FROM Game g " +
                "JOIN GamePlayer gp ON g.game_id = gp.game_id " +
                "WHERE gp.player_id = ? " +
                "ORDER BY g.start_time DESC";
        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, playerId);

            rsGames = st.executeQuery();
            while (rsGames.next()) {
                Game game = EntityInstantiator.instantiateGame(rsGames);
                games.add(game);
            }
            return games;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(st);
            Database.closeResultSet(rsGames);
        }
    }

    @Override
    public double getTotalPaidPerPlayer(int playerId) {
        PreparedStatement st = null;
        ResultSet rsPaid = null;

        String sql = "SELECT SUM(g.total_value / (" +
                "SELECT COUNT(*) FROM GamePlayer " +
                "WHERE game_id = g.game_id)) " +
                "FROM Game g " +
                "JOIN GamePlayer gp ON g.game_id = gp.game_id " +
                "WHERE gp.player_id = ?";
        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, playerId);
            rsPaid = st.executeQuery();

            if (rsPaid.next()) {
                return rsPaid.getDouble(1);
            }
            return 0.0;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeResultSet(rsPaid);
            Database.closeStatement(st);
        }
    }

    @Override
    public int getTotalTimePerPlayer(int playerId) {
        PreparedStatement st = null;
        ResultSet rsTime = null;

        String sql  = "SELECT SUM(g.duration_minutes) " +
                "FROM Game g " +
                "JOIN GamePlayer gp ON g.game_id = gp.game_id " +
                "WHERE gp.player_id = ?";

        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, playerId);
            rsTime = st.executeQuery();

            if (rsTime.next()) {
                return rsTime.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(st);
            Database.closeResultSet(rsTime);
        }
    }
}
