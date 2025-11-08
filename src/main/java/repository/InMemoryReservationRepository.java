package repository;

import domain.Reservation;
import domain.Screening;
import domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryReservationRepository implements ReservationRepository {

    private static final Map<Long, Reservation> store = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0L);

    @Override
    public Reservation save(Reservation reservation) {
        long newId = sequence.incrementAndGet();
        reservation.setId(newId);
        store.put(newId, reservation);
        return reservation;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Reservation> findByScreening(Screening screening) {
        List<Reservation> result = new ArrayList<>();
        
        for (Reservation reservation : store.values()) {
            if (reservation.getScreening().equals(screening)) {
                result.add(reservation);
            }
        }
        return result;
    }

    @Override
    public List<Reservation> findByUser(User user) {
        List<Reservation> result = new ArrayList<>();

        for (Reservation reservation : store.values()) {
            if (reservation.getUser().equals(user)) {
                result.add(reservation);
            }
        }
        return result;
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}