package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    private User testUser;
    private Screening testScreening;
    private List<Seat> testSeats;
    private Payment testPayment;
    private long testUsedPoint;
    private int testFinalPrice;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "testUser");

        Movie movie = new Movie("테스트 영화", 120);
        Place place = new Place("1관", LocalTime.MIN, LocalTime.MAX, new ArrayList<>());
        testScreening = new Screening(movie, place, LocalDateTime.of(2025, 11, 10, 10, 0));
        testSeats = List.of(new Seat(SeatGrade.S, "A", 1));
        testPayment = Payment.CREDIT_CARD;
        testUsedPoint = 500L;
        testFinalPrice = 20000;
    }

    @Test
    @DisplayName("Reservation 객체가 모든 값을 전달받아 정상적으로 생성된다.")
    void createReservationAndVerifyGetters() {


        Reservation reservation = new Reservation(
                testUser,
                testScreening,
                testSeats,
                testPayment,
                testUsedPoint,
                testFinalPrice
        );

        assertThat(reservation).isNotNull();
        assertThat(reservation.getUser()).isEqualTo(testUser);
        assertThat(reservation.getScreening()).isEqualTo(testScreening);
        assertThat(reservation.getSelectedSeats()).isEqualTo(testSeats);
        assertThat(reservation.getPayment()).isEqualTo(testPayment);
        assertThat(reservation.getUsedPoint()).isEqualTo(testUsedPoint);
        assertThat(reservation.getFinalPrice()).isEqualTo(testFinalPrice);

    }
}