package validate;

import domain.Reservation;
import domain.Screening;
import domain.Seat;
import repository.ReservationRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReservedSeatValidator {

    private final ReservationRepository reservationRepository;

    public ReservedSeatValidator(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void validate(Screening screening, List<Seat> selectedSeats) {

        List<Reservation> screeningReservations = reservationRepository.findByScreening(screening);

        Set<Seat> allReservedSeats = new HashSet<>();

        for (Reservation reservation : screeningReservations) {

            List<Seat> seatsFromThisReservation = reservation.getSelectedSeats();
            allReservedSeats.addAll(seatsFromThisReservation);
        }

        if (allReservedSeats.isEmpty()) {
            return;
        }

        for (Seat newSeat : selectedSeats) {
            if (allReservedSeats.contains(newSeat)) {
                throw new IllegalArgumentException(
                        "선택한 좌석 [" + newSeat.toString() + "]은(는) 이미 예약된 좌석입니다."
                );
            }
        }
    }
}