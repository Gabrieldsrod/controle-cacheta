package com.gabrieldsrod.controlecacheta.db.dao.impl;

import com.gabrieldsrod.controlecacheta.db.Database;
import com.gabrieldsrod.controlecacheta.db.DatabaseException;
import com.gabrieldsrod.controlecacheta.db.dao.PlayerDao;
import com.gabrieldsrod.controlecacheta.entities.Player;

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
        String sql = "INSERT INTO PLAYER (nome) VALUES (?)";
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
    public void deleteById(int id) {
        PreparedStatement st = null;
        String sql = "DELETE FROM PLAYER WHERE Player.player_id = ?";
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
    public Player getById(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM PLAYER WHERE Player.player_id = ?";
        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Player player = instantiatePlayer(rs);
                return player;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            Database.closeResultSet(rs);
            Database.closeStatement(st);
        }
    }

    @Override
    public List<Player> getAllPlayers() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Player> players = new ArrayList<>();

        String sql = "SELECT * FROM 'Player'";
        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                Player player = instantiatePlayer(rs);
                players.add(player);
            }
            return players;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeResultSet(rs);
            Database.closeStatement(st);
        }
    }

    private Player instantiatePlayer(ResultSet rs) throws SQLException {
        Player obj = new Player();
        obj.setId(rs.getInt("player_id"));
        obj.setName(rs.getString("nome"));

        return obj;
    }
}
