package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PaymentTest {

    @Test
    @DisplayName("신용카드로 결제 시 5% 할인 되어야 한다.")
    void CreditCard() {
        int price = 10000;
        Payment paymentMethod = Payment.CREDIT_CARD;

        int discountedPrice = paymentMethod.applyDiscount(price);

        assertThat(discountedPrice).isEqualTo(9500);
    }

    @Test
    @DisplayName("현금으로 결제 시 2% 할인 되어야 한다.")
    void Cash() {
        int price = 10000;
        Payment paymentMethod = Payment.CASH;

        int discountedPrice = paymentMethod.applyDiscount(price);

        assertThat(discountedPrice).isEqualTo(9800);
    }
}