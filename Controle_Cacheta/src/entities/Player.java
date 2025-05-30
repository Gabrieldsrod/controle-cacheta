package entities;

public class Player {
    private String name;
    private int totalTimeMinutes;
    private double totalValueToPay;

    public Player(String name) {
        this.name = name;
        this.totalTimeMinutes = 0;
        this.totalValueToPay = 0.0;
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
}
