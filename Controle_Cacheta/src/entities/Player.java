package entities;

public class Player {
    private Integer id;
    private long totalTimeMinutes;
    private Double totalValueToPay;

    public Player(Integer id) {
        this.id = id;
        this.totalTimeMinutes = 0;
        this.totalValueToPay = 0.0;
    }

    public Integer getId() {
        return id;
    }

    public long getTotalTimeMinutes() {
        return totalTimeMinutes;
    }

    public Double getTotalValueToPay() {
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

    public void calculatePlayerPayments(double pricePerHour, long durationMinutes) {
        long horas = (long) Math.ceil(durationMinutes / 60.0);
        double totalPrice = horas * pricePerHour;
        this.totalValueToPay += totalPrice;
        this.totalTimeMinutes += (int) (horas * 60);
    }
}
