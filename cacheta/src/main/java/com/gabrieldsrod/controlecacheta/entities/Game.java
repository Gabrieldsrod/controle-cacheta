package main.java.com.gabrieldsrod.controlecacheta.entities;

import java.time.LocalDateTime;

public class Game {
    private final int tableNumber;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int durationMinutes;
    private final double totalValue;

    public Game(int tableNumber, LocalDateTime startTime, LocalDateTime endTime, int durationMinutes, double totalValue) {
        this.tableNumber = tableNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMinutes = durationMinutes;
        this.totalValue = totalValue;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getFormattedDuration() {
        int hours = durationMinutes / 60;
        int minutes = durationMinutes % 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    public String getFormattedTime() {
        return startTime.toLocalTime() + " - " + endTime.toLocalTime();
    }
}
