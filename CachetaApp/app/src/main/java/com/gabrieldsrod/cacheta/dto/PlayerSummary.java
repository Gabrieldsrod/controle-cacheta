package com.gabrieldsrod.cacheta.dto;

import java.util.List;

public class PlayerSummary {
    private String playerName;
    private String date;
    private List<MatchSummary> matches;
    private double totalToPay;

    public PlayerSummary(String playerName, String date, List<MatchSummary> matches, double totalToPay) {
        this.playerName = playerName;
        this.date = date;
        this.matches = matches;
        this.totalToPay = totalToPay;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getDate() {
        return date;
    }

    public List<MatchSummary> getMatches() {
        return matches;
    }

    public double getTotalToPay() {
        return totalToPay;
    }
}
