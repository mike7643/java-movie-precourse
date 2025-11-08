package service;

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
import policy.DiscountPolicy;
import policy.MovieDayDiscountPolicy;
import policy.TimeDiscountPolicy;
import repository.InMemoryReservationRepository;
import repository.ReservationRepository;
import validate.ReservedSeatValidator;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ReservationServiceIntegrationTest {

    private ReservationService reservationService;
    private ReservationRepository reservationRepository;
    private ReservedSeatValidator reservedSeatValidator;
    private PriceCalculator priceCalculator;
    private DiscountPolicy movieDayPolicy;
    private DiscountPolicy timePolicy;


    private User user;
    private Movie movie;
    private Screening screening;
    private Screening discountScreening;
    private Seat seatS1;
    private Seat seatA1;

    @BeforeEach
    void setUp() {
        reservationRepository = new InMemoryReservationRepository();

        ((InMemoryReservationRepository) reservationRepository).clearStore();

        movieDayPolicy = new MovieDayDiscountPolicy();
        timePolicy = new TimeDiscountPolicy();
        priceCalculator = new PriceCalculator(movieDayPolicy, timePolicy);
        reservedSeatValidator = new ReservedSeatValidator(reservationRepository);

        reservationService = new ReservationService(
                reservationRepository,
                reservedSeatValidator,
                priceCalculator
        );

        user = new User(1L, "정유민"); // 기본 포인트 1000
        movie = new Movie("설국열차", 120);
        Place place = null; // 테스트에 불필요

        // 일반 상영 (할인 없음)
        LocalDateTime normalTime = LocalDateTime.of(2025, 1, 1, 14, 0);
        screening = new Screening(movie, place, normalTime);

        // 모든 할인 적용 상영 (10일, 20시)
        LocalDateTime discountTime = LocalDateTime.of(2025, 1, 10, 20, 0);
        discountScreening = new Screening(movie, place, discountTime);

        seatS1 = new Seat(SeatGrade.S, "S", 1); // 18000원
        seatA1 = new Seat(SeatGrade.A, "A", 1); // 15000원
    }

    @Test
    @DisplayName("예매 생성(할인X, 포인트X) 통합 테스트 성공")
    void createReservation_Success_Simple() {
        List<Seat> selectedSeats = Collections.singletonList(seatS1);
        long usePoint = 0;
        Payment payment = Payment.CASH; // 2% 할인

        Reservation reservation = reservationService.createReservation(
                user, screening, selectedSeats, usePoint, payment
        );


        assertThat(reservation).isNotNull();
        assertThat(reservation.getId()).isEqualTo(1L);
        assertThat(reservation.getUser()).isEqualTo(user);

        // 2. 가격 검증 (18000 * 0.98 = 17640)
        assertThat(reservation.getFinalPrice()).isEqualTo(17640);
        assertThat(reservation.getUsedPoint()).isEqualTo(0);

        // 3. User 포인트 검증 (1000 - 0 = 1000)
        assertThat(user.getPoint()).isEqualTo(1000L);

        // 4. 리포지토리 저장 검증
        assertThat(reservationRepository.findById(1L)).isPresent();
    }

    @Test
    @DisplayName("모든 할인(무비데이, 시간) 및 포인트 사용 시 최종 가격 검증")
    void createReservation_Success_AllDiscountsAndPoints() {

        // S석(18000) + A석(15000) = 33000원
        List<Seat> selectedSeats = Arrays.asList(seatS1, seatA1);
        long usePoint = 500; // 500 포인트 사용
        Payment payment = Payment.CREDIT_CARD; // 5% 할인

        // discountScreening (무비데이 + 시간할인 적용)
        Reservation reservation = reservationService.createReservation(
                user, discountScreening, selectedSeats, usePoint, payment
        );


        // 기본가: 33000
        // 무비데이(10%): 33000 * 0.9 = 29700
        // 시간할인(2000): 29700 - 2,000 = 27700
        // 포인트(500): 27700 - 500 = 27200
        // 결제(5%): 27200 * 0.95 = 25840 (최종가)
        assertThat(reservation.getFinalPrice()).isEqualTo(25840);

        // 2. User 포인트 검증 (1000 - 500 = 500)
        assertThat(user.getPoint()).isEqualTo(500L);
    }

    @Test
    @DisplayName("기 예약된 좌석 예매 시도 시 Validator가 예외 발생")
    void createReservation_Fail_SeatAlreadyReserved() {

        // userA가 seatA1을 미리 예약함
        reservationService.createReservation(
                new User(2L, "userA"), screening, Collections.singletonList(seatA1), 0, Payment.CASH
        );

        // user(B)가 seatA1을 다시 예약하려 함
        assertThatThrownBy(() -> {
            reservationService.createReservation(
                    user, screening, Collections.singletonList(seatA1), 0, Payment.CASH
            );
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 예약된 좌석입니다");
    }

    @Test
    @DisplayName("보유 포인트보다 많이 사용 시 User 객체가 예외 발생")
    void createReservation_Fail_InsufficientPoints() {
        List<Seat> selectedSeats = Collections.singletonList(seatS1); // 18,000원
        long usePoint = 1001; // user는 1000 포인트 보유

        assertThatThrownBy(() -> {
            reservationService.createReservation(
                    user, screening, selectedSeats, usePoint, Payment.CASH
            );
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("보유한 포인트(1000)보다 많이 사용할 수 없다");
    }
}