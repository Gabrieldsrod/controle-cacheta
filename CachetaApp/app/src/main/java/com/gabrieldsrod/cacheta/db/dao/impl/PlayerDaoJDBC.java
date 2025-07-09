package com.gabrieldsrod.cacheta.db.dao.impl;

import com.gabrieldsrod.cacheta.db.Database;
import com.gabrieldsrod.cacheta.db.DatabaseException;
import com.gabrieldsrod.cacheta.db.dao.PlayerDao;
import com.gabrieldsrod.cacheta.entities.EntityInstantiator;
import com.gabrieldsrod.cacheta.entities.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJDBC implements PlayerDao {

    private Connection conn;

    public PlayerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void createPlayer(Player player) {
        PreparedStatement st = null;
        String sql = "INSERT INTO Player (nome) VALUES (?)";
        try {
            st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, player.getName());
            int rowsAffected = st.executeUpdate();

            if  (rowsAffected > 0) {
                ResultSet rsPlayer = st.getGeneratedKeys();
                if (rsPlayer.next()) {
                    int id = rsPlayer.getInt(1);
                    player.setId(id);
                }
                Database.closeResultSet(rsPlayer);
            }
            else {
                throw new DatabaseException("Unexpected error! No rows affected.");
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(st);
        }
    }

    @Override
    public void deletePlayerById(int id) {
        PreparedStatement st = null;
        String sql = "DELETE FROM Player WHERE Player.player_id = ?";
        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            int rowsAffected = st.executeUpdate();
            if  (rowsAffected > 0) {
                System.out.println("Id " + id + " has been deleted successfully.");
            } else {
                System.out.println("No player found with id " + id + ".");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(st);
        }
    }

    @Override
    public Player getPlayerById(int id) {
        PreparedStatement st = null;
        ResultSet rsPlayer = null;
        String sql = "SELECT * FROM Player WHERE player_id = ?";
        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, id);
            rsPlayer = st.executeQuery();
            if (rsPlayer.next()) {
                Player player = EntityInstantiator.instantiatePlayer(rsPlayer);
                return player;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            Database.closeResultSet(rsPlayer);
            Database.closeStatement(st);
        }
    }

    @Override
    public List<Player> getAllPlayers() {
        PreparedStatement st = null;
        ResultSet rsPlayer = null;
        List<Player> players = new ArrayList<>();

        String sql = "SELECT * FROM Player";
        try {
            st = conn.prepareStatement(sql);
            rsPlayer = st.executeQuery();

            while (rsPlayer.next()) {
                Player player = EntityInstantiator.instantiatePlayer(rsPlayer);
                players.add(player);
            }
            return players;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeResultSet(rsPlayer);
            Database.closeStatement(st);
        }
    }
}
