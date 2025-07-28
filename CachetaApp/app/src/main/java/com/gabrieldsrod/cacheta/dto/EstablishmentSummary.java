package com.gabrieldsrod.cacheta.dto;

public class EstablishmentSummary {
    private int occupiedTables;
    private double totalRaised;
    private String date;

    public EstablishmentSummary(int occupiedTables, double totalRaised, String date) {
        this.occupiedTables = occupiedTables;
        this.totalRaised = totalRaised;
        this.date = date;
    }

    public int getOccupiedTables() {
        return occupiedTables;
    }

    public double getTotalRaised() {
        return totalRaised;
    }

    public String getDate() {
        return date;
    }
}
