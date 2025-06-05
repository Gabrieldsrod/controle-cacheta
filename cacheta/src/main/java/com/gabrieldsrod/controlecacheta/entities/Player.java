package main.java.com.gabrieldsrod.controlecacheta.entities;

public class Player {
    private final Integer id;
    private int totalTimeMinutes;
    private double totalValueToPay;

    public Player(Integer id) {
        this.id = id;
        this.totalTimeMinutes = 0;
        this.totalValueToPay = 0.0;
    }

    public Integer getId() {
        return id;
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
}
