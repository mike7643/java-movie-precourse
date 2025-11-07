package service;


import domain.Seat;

import java.util.List;

public class PriceCalculator {

    private int calculateBasePrice(List<Seat> selectedSeats) {
        int totalPrice = 0;

        for(Seat seat : selectedSeats) {
            totalPrice += seat.getGrade().getPrice();
        }

        return totalPrice;
    }
}
