package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ScreeningTest {

    private Movie movie;
    private Place place;

    @BeforeEach
    void setUp() {
        movie = new Movie("쿵푸팬더", 130L);
        place = new Place("vip관", LocalTime.of(9, 0), LocalTime.of(23, 0), new ArrayList<>());
    }

    @Test
    @DisplayName("Screening 생성 시 주입된 객체들을 올바르게 반환한다.")
    void createScreening() {
        LocalDateTime startDayTime = LocalDateTime.of(2021, 1, 1, 10, 0);
        Screening screening = new Screening(movie, place, startDayTime);

        assertThat(screening.getMovie()).isEqualTo(movie);
        assertThat(screening.getPlace()).isEqualTo(place);
        assertThat(screening.getStartDayTime()).isEqualTo(startDayTime);
    }

    @Test
    @DisplayName("무비데이 조건: 매월 10일, 20일, 30일에 상영되는 영화는 10% 할인된다.")
    void isMovieDay(){
        LocalDateTime startDayTime = LocalDateTime.of(2025, 1, 1, 10, 0);
        Screening noMovieDay = new Screening(movie, place, startDayTime);
        assertThat(noMovieDay.isMovieDay()).isFalse();

        LocalDateTime movieDay = LocalDateTime.of(2025, 1, 10, 10, 0);
        Screening movieDayScreening = new Screening(movie, place, movieDay);
        assertThat(movieDayScreening.isMovieDay()).isTrue();

        LocalDateTime movieDay2 = LocalDateTime.of(2025, 1, 20, 10, 0);
        Screening movieDayScreening2 = new Screening(movie, place, movieDay2);
        assertThat(movieDayScreening2.isMovieDay()).isTrue();

        LocalDateTime movieDay3 = LocalDateTime.of(2025, 1, 30, 10, 0);
        Screening movieDayScreening3 = new Screening(movie, place, movieDay3);
        assertThat(movieDayScreening3.isMovieDay()).isTrue();
    }

    @Test
    @DisplayName("시간 조건: 오전 11시 이전 또는 오후 8시 이후에 시작하는 상영은 2,000원이 할인된다.")
    void isDiscountTime(){
        LocalDateTime startDayTime = LocalDateTime.of(2025, 1, 1, 10, 59);
        Screening noDiscountTime = new Screening(movie, place, startDayTime);
        assertThat(noDiscountTime.isDiscountTime()).isTrue();

        LocalDateTime discountTime = LocalDateTime.of(2025, 1, 1, 11, 0);
        Screening discountTimeScreening = new Screening(movie, place, discountTime);
        assertThat(discountTimeScreening.isDiscountTime()).isFalse();

        LocalDateTime discountTime2 = LocalDateTime.of(2025, 1, 1, 19, 59);
        Screening discountTimeScreening2 = new Screening(movie, place, discountTime2);
        assertThat(discountTimeScreening2.isDiscountTime()).isFalse();

        LocalDateTime discountTime3 = LocalDateTime.of(2025, 1, 1, 20, 0);
        Screening discountTimeScreening3 = new Screening(movie, place, discountTime3);
        assertThat(discountTimeScreening3.isDiscountTime()).isTrue();
    }
}