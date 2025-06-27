package com.gabrieldsrod.controlecacheta.db.dao.impl;

import com.gabrieldsrod.controlecacheta.db.DatabaseException;
import com.gabrieldsrod.controlecacheta.db.dao.PlayerDao;
import com.gabrieldsrod.controlecacheta.entities.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class PlayerDaoJDBC implements PlayerDao {

    private Connection conn;

    public PlayerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void createPlayer(Player player) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "INSERT INTO PLAYER (nome) VALUES (?)";
        try {
            st = conn.prepareStatement(sql);

            st.setString(1, player.getName());

            int rowsAffected = st.executeUpdate();

            if  (rowsAffected > 0) {
                ResultSet rsPlayer = st.getResultSet();
                if (rsPlayer.next()) {
                    int id = rsPlayer.getInt(1);
                    player.setId(id);
                }
            }
            else {
                throw new DatabaseException("Unexpected error! No rows affected.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Player getById(Integer id) {
        return null;
    }

    @Override
    public List<Player> getAllPlayers() {
        return List.of();
    }
}
