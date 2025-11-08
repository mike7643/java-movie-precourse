package service;

import domain.Payment;
import domain.Reservation;
import domain.Screening;
import domain.Seat;
import domain.User;
import repository.ReservationRepository;
import validate.ReservedSeatValidator;

import java.util.List;
import java.util.Optional;


public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservedSeatValidator reservedSeatValidator;
    private final PriceCalculator priceCalculator;


    public ReservationService(ReservationRepository reservationRepository,
                              ReservedSeatValidator reservedSeatValidator,
                              PriceCalculator priceCalculator) {
        this.reservationRepository = reservationRepository;
        this.reservedSeatValidator = reservedSeatValidator;
        this.priceCalculator = priceCalculator;
    }


    public Reservation createReservation(User user, Screening screening, List<Seat> selectedSeats, long usePoint, Payment payment) {

        reservedSeatValidator.validate(screening, selectedSeats);
        int finalPrice = priceCalculator.calculatePrice(
                screening, selectedSeats, usePoint, payment
        );

        user.usePoint(usePoint);


        Reservation reservation = new Reservation(
                user,
                screening,
                selectedSeats,
                payment,
                usePoint,
                finalPrice
        );

        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> findReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }


    public List<Reservation> findReservationsByUser(User user) {
        return reservationRepository.findByUser(user);
    }
}