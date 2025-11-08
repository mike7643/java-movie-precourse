package repository;

import domain.Reservation;
import domain.Screening;
import domain.User;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);

    List<Reservation> findByScreening(Screening screening);

    List<Reservation> findByUser(User user);

    void deleteById(Long id);
}