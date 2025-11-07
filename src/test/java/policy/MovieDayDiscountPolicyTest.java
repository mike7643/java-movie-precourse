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

import static org.junit.jupiter.api.Assertions.*;

class MovieDayDiscountPolicyTest {

    private MovieDayDiscountPolicy policy;
    private Movie movie;
    private Place place;

    private Screening screeningMovieDay;
    private Screening screeningNormalDay;

    @BeforeEach
    void setUp(){
        policy = new MovieDayDiscountPolicy();
        movie = new Movie("기생충", 120L);
        place = new Place("vip관", LocalTime.MIN, LocalTime.MAX, new ArrayList<>());

        LocalDateTime movieDay = LocalDateTime.of(2025, 1, 10, 10, 0);
        screeningMovieDay = new Screening(movie, place, movieDay);

        LocalDateTime normalDay = LocalDateTime.of(2025, 1, 1, 10, 0);
        screeningNormalDay = new Screening(movie, place, normalDay);
    }

    @Test
    @DisplayName("무비데이일 때 10000원에 10% 할인이 적용되어 9000원이 반환된다.")
    void applyDiscount_success(){
        int price = 10000;

        int discountedPrice = policy.applyDiscount(screeningMovieDay, price);

        assertEquals(9000, discountedPrice);
    }

    @Test
    @DisplayName("무비데이 아닐떄는 그냥 원가 반환된다.")
    void applyDiscount_noApply(){
        int price = 10000;

        int discountedPrice = policy.applyDiscount(screeningNormalDay, price);

        assertEquals(price, discountedPrice);
    }
}

