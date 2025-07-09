package com.gabrieldsrod.controlecacheta.db.dao.impl;

import com.gabrieldsrod.controlecacheta.db.Database;
import com.gabrieldsrod.controlecacheta.db.dao.GameDao;
import com.gabrieldsrod.controlecacheta.db.dao.GamePlayerDao;
import com.gabrieldsrod.controlecacheta.db.dao.PlayerDao;
import com.gabrieldsrod.controlecacheta.db.dao.TableDao;

public class DaoFactory {
    public static PlayerDao createPlayerDao() {
        return new PlayerDaoJDBC(Database.getConnection());
    }

    public static GameDao createGameDao() {
        return new GameDaoJDBC(Database.getConnection());
    }

    public static TableDao createTableDao() {
        return new TableDaoJDBC(Database.getConnection());
    }

    public static GamePlayerDao createGamePlayerDao() {
        return new GamePlayerDaoJDBC(Database.getConnection());
    }

}
