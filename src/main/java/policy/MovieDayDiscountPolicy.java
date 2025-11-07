package policy;

import domain.Screening;

public class MovieDayDiscountPolicy implements DiscountPolicy {

    private static final double DISCOUNT_RATE = 0.1;
    @Override
    public int applyDiscount(Screening screening, int price) {
        if(screening.isMovieDay())
            return (int) (price * (1 - DISCOUNT_RATE));

        return price;
    }
}
