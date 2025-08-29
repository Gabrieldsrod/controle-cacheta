package com.gabrieldsrod.cacheta.dto;

import androidx.annotation.Nullable;

import com.gabrieldsrod.cacheta.entities.Player;

public class TableSummary {
    private final int tableNumber;
    private final double totalRaised;
    @Nullable
    private final Player topPlayer;
    private final int topPlayerMinutesPlayed;

    public TableSummary(int tableNumber, double totalRaised, Player topPlayer, int topPlayerMinutesPlayed) {
        this.tableNumber = tableNumber;
        this.totalRaised = totalRaised;
        this.topPlayer = topPlayer;
        this.topPlayerMinutesPlayed = topPlayerMinutesPlayed;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public double getTotalRaised() {
        return totalRaised;
    }

    public Player getTopPlayer() {
        return topPlayer;
    }

    public int getTopPlayerMinutesPlayed() {
        return topPlayerMinutesPlayed;
    }
}
