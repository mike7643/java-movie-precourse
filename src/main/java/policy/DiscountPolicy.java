package policy;

import domain.Screening;

public interface DiscountPolicy {
    int applyDiscount(Screening screening, int price);
}
