package domain;

import java.time.LocalTime;
import java.util.List;

public class Place {
    private final String name;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final List<Seat> seats;

    public Place(String name, LocalTime openTime, LocalTime closeTime, List<Seat> seats) {
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.seats = seats;
    }

    public String getName() {
        return name;
    }
    public LocalTime getOpenTime() {
        return openTime;
    }
    public LocalTime getCloseTime() {
        return closeTime;
    }
    public List<Seat> getSeats() {
        return seats;
    }
}
