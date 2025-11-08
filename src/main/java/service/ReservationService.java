package service;

import domain.Payment;
import domain.Reservation;
import domain.Screening;
import domain.Seat;
import domain.User;
import repository.ReservationRepository;

import java.util.List;
import java.util.Optional;


public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }


    public Reservation createReservation(User user, Screening screening, List<Seat> selectedSeats, long usePoint, Payment payment) {
        // TODO: 좌석 검증 -> 가격 계산 -> 포인트 차감 -> 객체 생성 -> 저장
        throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
    }

    public Optional<Reservation> findReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }


    public List<Reservation> findReservationsByUser(User user) {
        return reservationRepository.findByUser(user);
    }
}