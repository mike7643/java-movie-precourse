package domain;

import java.time.LocalDateTime;
import java.util.List;

public class Reservation {

    private long id;
    private final User user;
    private final Screening screening;
    private final List<Seat> selectedSeats;
    private final Payment payment;
    private final long usedPoint;
    private final int finalPrice;
    private final LocalDateTime reservationDateTime;

    public Reservation(User user, Screening screening, List<Seat> selectedSeats, Payment payment, long usedPoint, int finalPrice) {
        this.user = user;
        this.screening = screening;
        this.selectedSeats = selectedSeats;
        this.payment = payment;
        this.usedPoint = usedPoint;
        this.finalPrice = finalPrice;
        this.reservationDateTime = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }

    public Screening getScreening() {
        return screening;
    }

    public List<Seat> getSelectedSeats() {
        return selectedSeats;
    }

    public Payment getPayment() {
        return payment;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public long getUsedPoint() {
        return usedPoint;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }
}
