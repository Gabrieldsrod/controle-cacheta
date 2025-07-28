package com.gabrieldsrod.cacheta.dto;

public class MatchSummary {

    private int tableId;
    private String time;
    private String durationTime;
    private double valueToPay;

    public MatchSummary(int tableId, String time, String durationTime, double valueToPay) {
        this.tableId = tableId;
        this.time = time;
        this.durationTime = durationTime;
        this.valueToPay = valueToPay;
    }

    public int getTableId() {
        return tableId;
    }

    public String getTime() {
        return time;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public double getValueToPay() {
        return valueToPay;
    }
}
