package com.gabrieldsrod.cacheta.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "GamePlayer",
        primaryKeys = {"game_id", "player_id"},
        foreignKeys = {
        @ForeignKey(entity = Game.class,
        parentColumns = "id",
        childColumns = "game_id",
        onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Player.class,
        parentColumns = "id",
        childColumns = "player_id",
        onDelete = ForeignKey.CASCADE)
        })
public class GamePlayer {
    @ColumnInfo(name = "game_id", index = true)
    private int gameId;
    @ColumnInfo(name = "player_id", index = true)
    private int playerId;

    public GamePlayer(int gameId, int playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
