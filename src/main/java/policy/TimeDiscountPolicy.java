package policy;

import domain.Screening;

public class TimeDiscountPolicy implements DiscountPolicy{

    private static final int DISCOUNT_AMOUNT = 2000;

    @Override
    public int applyDiscount(Screening screening, int price) {
        if(screening.isDiscountTime())
            return Math.max(price - DISCOUNT_AMOUNT, 0);

        return price;
    }
}
