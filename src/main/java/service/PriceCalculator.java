package service;

import domain.Payment;
import domain.Screening;
import domain.Seat;
import policy.DiscountPolicy;

import java.util.List;

public class PriceCalculator {

    private final DiscountPolicy moiveDayDiscountPolicy;
    private final DiscountPolicy timeDiscountPolicy;

    public PriceCalculator(DiscountPolicy moiveDayDiscountPolicy, DiscountPolicy timeDiscountPolicy) {
        this.moiveDayDiscountPolicy = moiveDayDiscountPolicy;
        this.timeDiscountPolicy = timeDiscountPolicy;
    }

    public int calculatePrice(Screening screening, List<Seat> selectedSeats, long usedPoint, Payment payment) {
        int price = calculateBasePrice(selectedSeats);

        price = moiveDayDiscountPolicy.applyDiscount(screening, price);
        price = timeDiscountPolicy.applyDiscount(screening,price);

        if(usedPoint>price){
            throw new IllegalArgumentException("사용하려는 포인트(" + usedPoint + ")가 결제 금액(" + price + ")보다 많습니다.");
        }

        price -= (int) usedPoint;

        price = payment.applyDiscount(price);

        return price;
    }

    private int calculateBasePrice(List<Seat> selectedSeats) {
        int totalPrice = 0;

        for(Seat seat : selectedSeats) {
            totalPrice += seat.getGrade().getPrice();
        }

        return totalPrice;
    }
}
