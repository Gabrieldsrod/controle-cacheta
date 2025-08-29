package com.gabrieldsrod.cacheta.dto;

import java.util.List;

public class EstablishmentSummary {
    private int occupiedTables;
    private double totalRaised;
    private String date;
    private List<TableSummary> tableSummaries;

    public EstablishmentSummary(int occupiedTables, double totalRaised, String date, List<TableSummary> tableSummaries) {
        this.occupiedTables = occupiedTables;
        this.totalRaised = totalRaised;
        this.date = date;
        this.tableSummaries = tableSummaries;
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

    public List<TableSummary> getTableSummaries() {
        return tableSummaries;
    }
}
