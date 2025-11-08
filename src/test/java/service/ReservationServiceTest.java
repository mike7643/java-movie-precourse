package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.InMemoryReservationRepository;
import repository.ReservationRepository;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationServiceTest {

    private ReservationRepository reservationRepository;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationRepository = new InMemoryReservationRepository();
        reservationService = new ReservationService(reservationRepository);
    }

    @Test
    @DisplayName("ReservationService가 의존성을 주입받아 성공적으로 생성된다.")
    void createReservationService() {
        assertThat(reservationService).isNotNull();
    }
}