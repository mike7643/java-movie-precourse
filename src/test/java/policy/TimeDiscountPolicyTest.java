package policy;

import domain.Movie;
import domain.Place;
import domain.Screening;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TimeDiscountPolicyTest {

    private TimeDiscountPolicy policy;
    private Movie movie;
    private Place place;

    private Screening screeningNormalTime;
    private Screening screeningDiscountTime;

    @BeforeEach
    void setUp(){
        policy = new TimeDiscountPolicy();

        movie = new Movie("기생충", 120L);
        place = new Place("vip관", LocalTime.MIN, LocalTime.MAX, new ArrayList<>());

        LocalDateTime normalDay = LocalDateTime.of(2025, 1, 1, 10, 0);
        screeningDiscountTime = new Screening(movie, place, normalDay);

        LocalDateTime movieDay = LocalDateTime.of(2025, 1, 10, 14, 0);
        screeningNormalTime = new Screening(movie, place, movieDay);
    }

    @Test
    @DisplayName("시간 할인 될 때 10000원에서 2000 할인 적용되어 8000이 반환된다.")
    void applyDiscount_success(){
        int price = 10000;
        int discountedPrice = policy.applyDiscount(screeningDiscountTime, price);
        assertThat(discountedPrice).isEqualTo(8000);
    }

    @Test
    @DisplayName("시간 할인 안 될 때 할인 적용되지 않고 반환된다.")
    void applyDiscount_noApply(){
        int price = 10000;
        int discountedPrice = policy.applyDiscount(screeningNormalTime, price);
        assertThat(discountedPrice).isEqualTo(price);
    }

    @Test
    @DisplayName("할인 후 가격이 0원 미만일 경우 0원이 반환된다.")
    void applyDiscount_zeroPrice(){
        int price = 0;
        int discountedPrice = policy.applyDiscount(screeningDiscountTime, price);
        assertThat(discountedPrice).isEqualTo(0);
    }
}