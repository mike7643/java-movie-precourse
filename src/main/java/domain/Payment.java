package domain;

public enum Payment {
    CREDIT_CARD(0.05), CASH(0.02);

    private final double discountRate;

    Payment(double discountRate) {
        this.discountRate = discountRate;
    }

    public int applyDiscount(int price){
        return (int) (price * (1 - discountRate));
    }
}
