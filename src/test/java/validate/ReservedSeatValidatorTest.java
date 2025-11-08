package validate;

import domain.Movie;
import domain.Payment;
import domain.Place;
import domain.Reservation;
import domain.Screening;
import domain.Seat;
import domain.SeatGrade;
import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.InMemoryReservationRepository;
import repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservedSeatValidatorTest {

    private ReservationRepository reservationRepository;
    private ReservedSeatValidator validator;


    private User user;
    private Movie movie;
    private Place place;
    private Screening screening1;
    private Screening screening2;
    private Seat seatA1;
    private Seat seatA2;
    private Seat seatB1;

    @BeforeEach
    void setUp() {
        reservationRepository = new InMemoryReservationRepository();
        validator = new ReservedSeatValidator(reservationRepository);

        user = new User(1L, "정유민");
        movie = new Movie("설쿡열차", 120);
        place = null;

        LocalDateTime time1 = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime time2 = LocalDateTime.of(2025, 1, 1, 13, 0);
        screening1 = new Screening(movie, place, time1);
        screening2 = new Screening(movie, place, time2);

        seatA1 = new Seat(SeatGrade.S, "A", 1);
        seatA2 = new Seat(SeatGrade.S, "A", 2);
        seatB1 = new Seat(SeatGrade.A, "B", 1);

        List<Seat> reservedSeats = Arrays.asList(seatA1, seatA2);
        Reservation existingReservation = new Reservation(
                user, screening1, reservedSeats, Payment.CASH, 0, 30000
        );
        reservationRepository.save(existingReservation);
    }

    @Test
    @DisplayName("기 예약된 좌석(A1)을 포함하여 예매 시도 시 IllegalArgumentException 발생")
    void validate_SelectingAlreadyReservedSeat_ThrowsException() {

        //B1은 가능 하지만, A1은 불가능하다.
        List<Seat> newSeatsToSelect = Arrays.asList(seatB1, seatA1);

        assertThatThrownBy(() -> validator.validate(screening1, newSeatsToSelect))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 예약된 좌석입니다");
    }

    @Test
    @DisplayName("예약 가능한 좌석(B1)만 예매 시도 시 예외가 발생하지 않음")
    void validate_SelectingAvailableSeats_DoesNotThrowException() {
        List<Seat> newSeatsToSelect = Collections.singletonList(seatB1);

        assertThatCode(() -> validator.validate(screening1, newSeatsToSelect))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("다른 상영(screening2)에서 A1 좌석 예매 시도 시 예외가 발생하지 않음")
    void validate_SelectingSeatReservedInDifferentScreening_DoesNotThrowException() {
        List<Seat> newSeatsToSelect = Collections.singletonList(seatA1);

        assertThatCode(() -> validator.validate(screening2, newSeatsToSelect))
                .doesNotThrowAnyException();
    }
}