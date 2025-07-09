package com.gabrieldsrod.cacheta.db.dao.impl;

import com.gabrieldsrod.cacheta.db.Database;
import com.gabrieldsrod.cacheta.db.dao.GameDao;
import com.gabrieldsrod.cacheta.db.dao.GamePlayerDao;
import com.gabrieldsrod.cacheta.db.dao.PlayerDao;
import com.gabrieldsrod.cacheta.db.dao.TableDao;

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
