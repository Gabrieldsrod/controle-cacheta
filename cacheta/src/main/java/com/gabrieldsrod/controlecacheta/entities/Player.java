package com.gabrieldsrod.controlecacheta.entities;

public class Player {
    private final Integer id;
    private final String name;
    private int totalTimeMinutes;
    private double totalValueToPay;

    public Player(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.totalTimeMinutes = 0;
        this.totalValueToPay = 0.0;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTotalTimeMinutes() {
        return totalTimeMinutes;
    }

    public double getTotalValueToPay() {
        return totalValueToPay;
    }

    public void addTime(int minutes) {
        this.totalTimeMinutes += minutes;
    }

    public void addValue(double value) {
        this.totalValueToPay += value;
    }

    public void reset() {
        this.totalTimeMinutes = 0;
        this.totalValueToPay = 0.0;
    }

    public void calculatePlayerPayments(double pricePerHour, int durationMinutes) {
        int horas = Math.max(1, (int) Math.ceil(durationMinutes / 60.0));
        double totalPrice = horas * pricePerHour;
        this.totalValueToPay += totalPrice;
        this.totalTimeMinutes += horas * 60;
    }

    public String getFormattedDuration() {
        int hours = totalTimeMinutes / 60;
        int minutes = totalTimeMinutes % 60;
        return String.format("%02d:%02d", hours, minutes);
    }
}
