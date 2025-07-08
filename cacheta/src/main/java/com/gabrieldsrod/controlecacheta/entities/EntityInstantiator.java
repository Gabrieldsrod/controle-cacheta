package com.gabrieldsrod.controlecacheta.entities;

import com.gabrieldsrod.controlecacheta.db.dao.TableDao;
import com.gabrieldsrod.controlecacheta.db.dao.impl.DaoFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EntityInstantiator {
    public static Game instantiateGame(ResultSet rs) throws SQLException {
        int gameId = rs.getInt("game_id");
        int tableId = rs.getInt("table_id");
        LocalDateTime start_time = LocalDateTime.parse(rs.getString("start_time"));
        LocalDateTime end_time = LocalDateTime.parse(rs.getString("end_time"));
        int duration = rs.getInt("duration_minutes");
        double total_value = rs.getDouble("total_value");

        TableDao tableDao = DaoFactory.createTableDao();
        Table table = tableDao.getTableById(tableId);

        return new Game(gameId, table, start_time, end_time, duration, total_value);
    }

    public static Player instantiatePlayer(ResultSet rs) throws SQLException {
        Player obj = new Player();
        obj.setId(rs.getInt("player_id"));
        obj.setName(rs.getString("nome"));

        return obj;
    }

    public static Table instantiateTable(ResultSet rs) throws SQLException {
        Table obj = new Table();
        obj.setTableNumber(rs.getInt("table_id"));
        obj.setStatus(rs.getString("status"));

        return obj;
    }
}
