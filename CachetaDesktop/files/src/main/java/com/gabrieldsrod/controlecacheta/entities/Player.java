package com.gabrieldsrod.controlecacheta.entities;

import java.util.Objects;

public class Player {
    private int id;
    private int totalTimeMinutes;
    private String name;
    private double totalValueToPay;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Player(int id, String name, int totalTimeMinutes, double totalValueToPay) {
        this.id = id;
        this.name = name;
        this.totalTimeMinutes = totalTimeMinutes;
        this.totalValueToPay = totalValueToPay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id && totalTimeMinutes == player.totalTimeMinutes && Double.compare(totalValueToPay, player.totalValueToPay) == 0 && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalTimeMinutes, name, totalValueToPay);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", totalTimeMinutes=" + totalTimeMinutes +
                ", name='" + name + '\'' +
                ", totalValueToPay=" + totalValueToPay +
                '}';
    }
}
