package com.gabrieldsrod.cacheta.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.gabrieldsrod.cacheta.converter.Converters;
import com.gabrieldsrod.cacheta.db.dao.GameDao;
import com.gabrieldsrod.cacheta.db.dao.GamePlayerDao;
import com.gabrieldsrod.cacheta.db.dao.PlayerDao;
import com.gabrieldsrod.cacheta.db.dao.TableDao;
import com.gabrieldsrod.cacheta.entities.Game;
import com.gabrieldsrod.cacheta.entities.GamePlayer;
import com.gabrieldsrod.cacheta.entities.Player;
import com.gabrieldsrod.cacheta.entities.Table;

@Database(entities = {Player.class, Table.class, Game.class, GamePlayer.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase database;
    private static String databaseName = "cacheta";

    public synchronized static AppDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, databaseName)
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }

    public abstract PlayerDao playerDao();
    public abstract TableDao tableDao();
    public abstract GameDao gameDao();
    public abstract GamePlayerDao gamePlayerDao();
}